import copy
import sys
from colorama import Fore, Style
from go import Go
from typing import Any
import os
import bot as bt
import time
import click

num_players = -1
board_size = -1
superko = False
bot_type = 0

def clear() -> None:
    '''
    Clears the terminal window.
    Inputs: None
    Returns: None
    '''
    if os.name == 'nt':
        os.system('cls')
    else:
        os.system('clear')

def print_board(gme: Go, colors_dict: dict[int, Any]) -> None:
    '''
    Prints the current board to the terminal.
    Inputs: gme = Go implementation of game, colors_dict = dictionary mapping players to colors
    Outputs: None
    '''
    for r in range(len(gme.grid)):
        row = ''
        for c in range(len(gme.grid[r])):
            if gme.grid[r][c]:
                if c==0:
                    row += f"{colors_dict[gme.grid[r][c]]}{gme.grid[r][c]}{Style.RESET_ALL}"
                else:
                    row += f'{Fore.LIGHTBLACK_EX}───' + f'{colors_dict[gme.grid[r][c]]}{gme.grid[r][c]}{Style.RESET_ALL}'
            else:
                if c==0:
                    row += f'{Fore.LIGHTBLACK_EX}●{Style.RESET_ALL}'
                else:
                    row += f'{Fore.LIGHTBLACK_EX}───●{Style.RESET_ALL}'
        print(row)
        if r != len(gme.grid)-1:
            print(f'{Fore.LIGHTBLACK_EX}│   '*(len(gme.grid[r])-1) + '│')
    print()

@click.command("tui")
@click.option('-n', '--players', default=2, type=int, help='Number of players in the game.')
@click.option('-s', '--size', default=19, type=int, help='Size of the game board.')
@click.option('--super-ko/--simple-ko', default=False, type=bool, help='The version of Go being played.')
def initialize_variables(players, size, super_ko) -> None:
    '''
    Initializes the game variables using the click library.
    Inputs: players = number of players, size = board size, super_ko = version of Go being played.
    Returns: None
    '''
    global num_players, board_size, superko
    num_players = players
    board_size = size
    superko = super_ko

def event_loop(game: Go) -> None:
    '''
    The event loop for text-based interface of Go.
    Inputs: game = Go implementation of game.
    Returns: None
    '''
    running = True
    skipped = False
    invalid = False
    players = [i for i in range(1, num_players+1)]
    colors = [Fore.RED, Fore.GREEN, Fore.LIGHTBLUE_EX, Fore.MAGENTA, Fore.CYAN, Fore.YELLOW, 
              Fore.LIGHTCYAN_EX, Fore.LIGHTRED_EX, Fore.LIGHTYELLOW_EX]
    colors_dict = {}
    col_index = 0
    for i in range(len(players)):
        colors_dict[players[i]] = colors[col_index]
        if col_index == len(colors)-1:
            col_index = 0
        else:
            col_index += 1
    while running:
        i = 0
        while i < len(players):
            clear()
            print('"Welcome to Go: the best board game in history. I hope you really enjoy it." -Neil Pandya, founder')
            print("● By the way, at any time, type 'quit' to stop the game.")
            print("● The current player should always type the desired position with the")
            print("  row number followed by a column number, separated by a space to make a move.")
            print("● To pass the current turn, press enter. If there are invalid arguments, the program will reprompt.")
            if bot_type > 0:
                print("● In this game version, you are player 1, playing against a bot, who is player 2.") 
                print("● Please be patient as the bot thinks of a move. You will be asked to make a move only when the bot is done. \n\n")
            else:
                print('\n\n')
            print_board(game, colors_dict)
            response = ''
            if skipped:
                if players[i-1] == 2 and bot_type > 0:
                    print("The BOT will pass their turn.")
                else:
                    print("Passing player " + str(players[i-1]) + "'s turn.")
                    if not bot_type > 0:
                        response = input("It is now player " + str(players[i]) + "'s turn. \n > ")
                skipped = False
            elif invalid:
                response = input("Invalid response for player " + str(players[i]) + ". Try again. \n > ")
                invalid = False
            elif (bot_type > 0 and players[i] != 2) or not bot_type > 0:
                response = input("It is now player " + str(players[i]) + "'s turn. \n > ")
            if players[i] == 2 and bot_type > 0:
                print("BOT is thinking...")
                time.sleep(1)
                if bot_type == 1:
                    response = bt.aggressive_bot(game, players[i])
                elif bot_type == 2:
                    response = bt.smart_bot(game, players[i])
                elif bot_type == 3:
                    response = bt.random_bot(game, players[i])
                if response is None:
                    response = ''
                else:
                    response = str(response[0]) + " " + str(response[1])
            try:
                if response == 'quit':
                    running = False
                    break
                elif response == '':
                    game.pass_turn()
                    skipped = True
                else:
                    response_list = response.split(' ')
                    response_list = [int(value) for value in response_list]
                    row, col = response_list[0], response_list[1]
                    if len(response_list) == 2 and game.legal_move((row, col)):
                        if game.simulate_move((row, col)).grid[row][col] == None:
                            copy_board = copy.deepcopy(game.grid)
                            copy_board[row][col] == players[i]
                            clear()
                            print_board(copy_board, colors_dict)
                            print("WARNING: This move is a sacrifice, and the next player's turn will follow.")
                            time.sleep(2)
                        game.apply_move((row, col))
                    else:
                        raise ValueError
                if game.done:
                    running = False
                    break
            except:
                invalid = True
                i -= 1
            i += 1
    clear()
    print("Here is the final game state: ")
    print_board(game, colors_dict)
    print('\n')
    print("The game is over, and is about to close automatically in a few seconds. Don't worry.")
    time.sleep(4)
    clear()
    for person in game.outcome:
        print("Player " + str(person) + " won with a score of " + str(game.scores()[person]))
    print("Thanks again for playing Go: the best board game in history. Play again?")



if __name__ == '__main__':
    reprompt = False
    initialize_variables(standalone_mode=False)

    if num_players == 2:
        clear()
        print("You indicated a 2 player game, so you have the option to play against a bot.")
        print("Just keep in mind that bots will take longer to make a move with larger board sizes.")
        response = input("Would you like the second player to be a bot? Type 'yes' if so. Otherwise press enter. \n > ")
        if response == 'yes':
            while True:
                clear()
                if reprompt:
                    print("Invalid Response. Please try again.")
                    reprompt = False
                response = input("Which bot would you like to play against? Type 'aggressive', 'smart', or 'random'. \n > ")
                if response == 'aggressive':
                    bot_type = 1
                    break
                elif response == 'smart':
                    bot_type = 2
                    break
                elif response == 'random':
                    bot_type = 3
                    break
                else:
                    reprompt = True

    game = Go(board_size, num_players, superko)
    event_loop(game)