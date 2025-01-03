import sys
import pygame
from bot import smart_bot
from go import Go
from math import sqrt
from typing import Optional
import click

pygame.init()

WIDTH, HEIGHT = 950, 950
SCREEN = pygame.display.set_mode((WIDTH, HEIGHT), flags=pygame.SCALED, vsync=1)
WHITE = (255, 255, 255)
BOARD_COLOR = (220,179,92)
BLACK = (0, 0, 0)
RED = (255, 0, 0)
GREEN = (0, 255, 0)
BLUE = (0, 0, 255)
PURPLE = (160, 32, 240)
PINK = (255, 192, 203)
GOLD = (255, 215, 0)
SILVER = (192, 192, 192)
YELLOW = (255, 255, 0)
HINT_YELLOW = (255, 195, 11)

PLAYER_COLORS = [WHITE, BLACK, RED, GREEN, BLUE, PURPLE, PINK, GOLD, SILVER]

@click.command("gui")
@click.option('-n', '--players', default=2, type=int, help='Number of players in the game.')
@click.option('-s', '--size', default=19, type=int, help='Size of the game board.')
@click.option('--super-ko/--simple-ko', default=False, type=bool, help='The version of Go being played.')

def initialize_variables(players, size, super_ko) -> None:
    '''
    Initializes the game variables using the click library.
    Inputs: players = number of players, size = board size, super_ko = version of Go being played.
    Returns: None
    '''
    global NUM_PLAYERS, BOARD_SIZE, SUPERKO
    NUM_PLAYERS = players
    BOARD_SIZE = size
    SUPERKO = super_ko

initialize_variables(standalone_mode = False)

PIECE_SIZE = 600 / BOARD_SIZE
if PIECE_SIZE > 150:
    PIECE_SIZE = 150

hover_indicator = pygame.rect.Rect(0, 0, PIECE_SIZE / 4, PIECE_SIZE / 4)
board = Go(BOARD_SIZE + 1, NUM_PLAYERS, SUPERKO)

big_font = pygame.font.Font('freesansbold.ttf', int(WIDTH / 10))
small_font = pygame.font.Font('freesansbold.ttf', int(WIDTH / 20))

hint = False
running = True

init_time = pygame.time.get_ticks()
move = None

def draw_text(text: str, font: pygame.font.Font, 
              text_cords: tuple[int, int]) -> None:
    '''
    Draws the given text using the given font

    Inputs:
        text [str]: refrence text to be drawn
        font [pygame.font.Font]: font that will represent the text
        text_cords [tuple[int, int]]: coordinates of the text

    Returns [None]
    '''
    new_text = font.render(text, True, WHITE)
    new_text_rect = new_text.get_rect()
    new_text_rect.center = text_cords
    SCREEN.blit(new_text, new_text_rect)

class Piece:
    '''
    Class representing each piece on the board

    Attributes:
        cord [tuple[float, float]]: tuple containing the cordinate of the 
        piece in relation to the screen
        pos [tuple[int, int]]: tuple containing position of the piece in
        relation to the grid
        player [Optional(int)]: the player that the piece belongs to
        rect [pygame.rect.Rect]: rect object that contains position and
        dimensions of piece
    '''
    pos: tuple[int, int]
    cord: tuple[float, float]
    r: int
    c: int
    player = Optional[int]
    rect = pygame.rect.Rect

    def __init__(self, cord: tuple[float, float], pos: tuple[int, int]):
        self.pos = pos
        self.r, self.c = self.pos
        self.player = None
        self.rect = pygame.rect.Rect(0, 0, PIECE_SIZE, PIECE_SIZE)
        self.hover_rect = pygame.rect.Rect(0, 0, PIECE_SIZE / 1.5, PIECE_SIZE / 1.5)
        self.rect.center = cord
        self.hover_rect.center = cord


    def draw(self) -> None:
        '''
        Draws the piece onto the screen 
        '''
        if (check_click(self.hover_rect, pygame.mouse.get_pos()) 
            and board.legal_move(self.pos)):
            hover_indicator.center = self.rect.center
            draw_borders(hover_indicator, 0, WHITE)
        if self.player is not None and board.grid[self.r][self.c] is not None:
            draw_borders(self.rect, 10000, PLAYER_COLORS[self.player - 1])

        
    def update(self, event: pygame.event) -> None:
        '''
        Updates the piece in accordance to the given pygame event

        Inputs:
            event [pygame.event]: pygame event that has occured

        Returns [None]
        '''
        global hint
        if event.type == pygame.MOUSEBUTTONDOWN and event.button == 1:
            if check_click(self.rect, event.pos) and board.legal_move(self.pos):
                self.player = board.turn
                board.apply_move(self.pos)
                hint = False

    
    def change_player(self, player: int) -> bool:
        '''
        Changes the player from None to the given integer. If player to be
        changed is not None, then nothing happens

        Inputs:
            player[int]: player number

        Returns [bool]: if the piece was succefully changed 
        '''
        if self.player is not None:
            return False
        self.player = player
        return True
    

def generate_winners(board: Go) -> str:
    '''
    Generates a string representation of the winner(s) of the game

    board [Go]: board containing all of the pieces

    Returns [str]: string representation of the winner(s)
    '''
    text = 'Winner is Player '
    for winner in board.outcome:
        text = text + str(winner) + ', '
    
    return text[:-2]
        

GRID_START = WIDTH / 6
if BOARD_SIZE == 1:
    GRID_START = (WIDTH / BOARD_SIZE) - (WIDTH / 5)
GRID_SIZE = WIDTH - (GRID_START * 2)
lines = []
for x in range(BOARD_SIZE + 1):
    start_pos = (GRID_START, GRID_START + x * (GRID_SIZE / BOARD_SIZE))
    end_pos = (WIDTH-GRID_START, start_pos[1])
    lines.append([start_pos, end_pos])
    lines.append([start_pos[::-1], end_pos[::-1]])

turn_indicator = pygame.rect.Rect(0, 0, GRID_START / 2, GRID_START / 2)
turn_indicator.center = (turn_indicator.height / 1.5, turn_indicator.height / 1.5)

pass_button = pygame.rect.Rect(0, 0, WIDTH / 3, GRID_START / 3)
pass_button.bottom = HEIGHT
pass_button.centerx = WIDTH / 2

hint_button = pygame.rect.Rect(0, 0, GRID_START / 2, GRID_START / 2)
hint_button.center = (HEIGHT - turn_indicator.centerx, turn_indicator.centery)
hint_indicator = pygame.rect.Rect((0, 0), hover_indicator.size)

pieces = []
pieces_pos = []

for idx, line in enumerate(lines):
    x, y = line[0]
    for i in range(BOARD_SIZE + 1):
        new_pos = (x + i * (GRID_SIZE / BOARD_SIZE), y)
        if not new_pos in pieces_pos and idx % 2 == 0:
            new_piece = Piece(new_pos, (int(idx / 2), i))
            pieces.append(new_piece)
            pieces_pos.append(new_pos)


for idx1, row in enumerate(board.grid):
    offset = idx1 * (BOARD_SIZE + 1)
    for idx2, col in enumerate(row):
        pieces[idx2 + offset].change_player(col)


def check_click(rect: pygame.Rect, pos: tuple[int, int]) -> bool:
    '''
    Checks if a given click is within the bounds of the given circle

    rect [pygame.Rect]: circle to be checked
    pos [tuple[int, int]]: posisiton of the click

    Returns [bool]: True if the click if valid, otherwise false
    '''
    x, y = pos
    x_dis = abs(x - rect.centerx)
    y_dis = abs(y - rect.centery)
    return rect.height / 2 >= sqrt((x_dis ** 2) + (y_dis ** 2))


def draw_borders(rect: pygame.Rect, border_radius: int, 
                 color: tuple[int, int, int]) -> None:
    '''
    Draws the given rect and adds a black border to the edge

    rect [pygame.Rect]: circle to be checked
    border_radius [int]: the size that the border should be
    color [tuple[int, int, int]]: color represented through an RGB value

    Returns None
    '''
    pygame.draw.rect(SCREEN, color, rect, 0, border_radius)
    pygame.draw.rect(SCREEN, BLACK, rect, 3, border_radius)


while running:
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            running = False

        if not board.done:
            if event.type == pygame.MOUSEBUTTONDOWN and event.button == 1:
                if pass_button.collidepoint(event.pos):
                    hint = False
                    board.pass_turn()
                if hint_button.collidepoint(event.pos) and NUM_PLAYERS <= 2:
                    hint = True
                    draw_borders(hint_button, -1, SILVER)
                    pygame.display.flip()
                    move = smart_bot(board, board.turn)
                    init_time = pygame.time.get_ticks()
            
            for piece in pieces:
                piece.update(event)

    if board.done:
        SCREEN.fill(BLACK)
        draw_text('Game Over', big_font, (WIDTH / 2, HEIGHT / 3))
        draw_text(generate_winners(board), big_font, (WIDTH / 2, HEIGHT / 2.25))
        for x in board.scores():
            draw_text(f'Player {x}: {board.scores()[x]}', 
                      small_font, (WIDTH / 2, (HEIGHT / 2) + x * (WIDTH / 15)))

    
    else:
        SCREEN.fill(BOARD_COLOR)
        for line in lines:
            pygame.draw.line(SCREEN, BLACK, line[0], line[1], 3)
        for piece in pieces:
            piece.draw()
        draw_borders(turn_indicator, 10000, PLAYER_COLORS[board.turn - 1])
        draw_borders(pass_button, -1, RED)
        if NUM_PLAYERS <= 2:
            draw_borders(hint_button, -1, HINT_YELLOW)
            if hint:
                draw_borders(hint_button, -1, SILVER)
                if move is None:
                    if pygame.time.get_ticks() - init_time >= 1000:
                        draw_borders(pass_button, -1, YELLOW)
                        if pygame.time.get_ticks() - init_time >= 2000:
                            init_time = pygame.time.get_ticks()
                else:
                    move_x, move_y = move
                    piece = pieces[move_x * (BOARD_SIZE + 1) + move_y]
                    hint_indicator.center = piece.rect.center
                    if pygame.time.get_ticks() - init_time >= 1000:
                        draw_borders(hint_indicator, -1, YELLOW)
                        if pygame.time.get_ticks() - init_time >= 2000:
                            init_time = pygame.time.get_ticks()
        else:
            draw_borders(hint_button, -1, SILVER)
        mouse_pos = pygame.mouse.get_pos()
        if hint_button.collidepoint(mouse_pos) and NUM_PLAYERS <= 2 and not hint:
            draw_borders(hint_button, -1, YELLOW)

    pygame.display.update()

pygame.quit()


