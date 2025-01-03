import random
import sys
from typing import Optional
from go import Go
import click



def smart_bot(board: Go, player) -> Optional[tuple[int, int]]:
    """
    inputs: board, player number
    outputs: tuple of best move
    """
    best_move = None
    best_value = float("-inf")
    moves = board.legal_moves_list
    if not moves:
        return None
    for move in moves:
        new_board = board.simulate_move(move)
        opponent_moves = new_board.legal_moves_list
        total_pieces = sum([new_board.simulate_move(opponent_move).scores()\
        [player] for opponent_move in opponent_moves]) 
        if len(opponent_moves) == 0:
            avg_pieces = new_board.scores()[player]
        else:
            avg_pieces = total_pieces / len(opponent_moves)
        if avg_pieces > best_value:
            best_move = move
            best_value = avg_pieces
    return best_move

def aggressive_bot(board: Go, player) -> Optional[tuple[int, int]]:
    """
    Checks to see which opponent piece groupings take the least stones to 
    capture. If there are multiple piece groupings that can be captured with the
    same amount of stones, it checks which grouping has the most stones in it.
    output: tuple of most optimal move according to the strategy
    *** Note: this bot works, it just takes a long time ***
    """    
    #heuristic portion
    if len(board.legal_moves_list) == board.size * board.size:
        return 4,4
    moves = board.legal_moves_list
    if player == 1:
        opp = 2
    elif player == 2:
        opp = 1
    if not moves:
        return None
    best_moves = []
    best_value = float("inf")
    highest_visited = float("-inf")
    for move in moves:  
        for neighbor in board.neighbors(move):
            if board.piece_at(neighbor) is None:
                num = float("inf")
            elif board.piece_at(neighbor) != player:
                num, visited, _ = num_to_capture(board, neighbor)
                highest_visited = visited
                if num < best_value:
                    best_value = num
                    best_moves = [move]
                    highest_visited = visited
                elif (num == best_value) and (visited == highest_visited):
                    if move not in best_moves:
                        best_moves.append(move)
                elif (num == best_value) and (visited > highest_visited):
                    if move not in best_moves:
                        best_moves = [move]
                        highest_visited = visited
    """if there are multiple best moves, it uses the logic of smart_bot for the 
    best moves"""
    best_move = None
    best_value2 = float("-inf")
    if best_moves == []:
        best_moves = board.legal_moves_list
    for move in best_moves:
        new_board = board.simulate_move(move)
        opponent_moves = new_board.legal_moves_list
        total_pieces = sum([new_board.simulate_move(opponent_move).scores()\
        [player] - new_board.simulate_move(opponent_move).scores()\
        [opp] for opponent_move in opponent_moves]) 
        if len(opponent_moves) == 0:
            avg_pieces = new_board.scores()[player]
        else:
            avg_pieces = total_pieces / len(opponent_moves)
        if avg_pieces > best_value2:
            best_move = move
            best_value2 = avg_pieces
    return best_move

def num_to_capture(board: Go, pos, visited=None, visited_edge=None, \
count: int = None) -> tuple[int, int]:
    """
    returns how many stones are needed to capture a group of stones
    inputs: board, pos, visited
    outputs: tuple[int, int]
    """
    if count is None:
        count = 0
    if visited is None:
        visited = [pos]
    if visited_edge is None:
        visited_edge = []
    for neighbor in board.neighbors(pos):
        if (board.piece_at(neighbor) is None) and \
        (neighbor not in visited_edge):
            count +=1
            visited_edge.append(neighbor)
        elif (board.piece_at(neighbor) == board.piece_at(pos)) and \
        (neighbor not in visited):
            visited.append(pos)
            num_to_capture(board, neighbor, visited, visited_edge, count)
    return (count, len(visited), visited_edge)



def random_bot(board: Go, player) -> Optional[tuple[int, int]]:
    """
    inputs: board, player number
    outputs: tuple of best move
    """
    moves = board.legal_moves_list
    if not moves:
        return None
    choice = random.choice(moves)
    return choice

@click.command("bot")
@click.option('-n', '--num-games', default=20, type=int, help='Number of games to play')
@click.option('-s', '--board-size', default=6, type=int, help='Size of the game board')
@click.option('-1', '--bot1', default='random', type=click.Choice(['random', 'smart', 'aggressive']), help='Strategy for bot 1')
@click.option('-2', '--bot2', default='random', type=click.Choice(['random', 'smart', 'aggressive']), help='Strategy for bot 2')

def main(num_games, board_size, bot1, bot2):
    """
    runs the game
    inputs: none
    outputs: percent wins of player 1, player 2, ties, and average moves
    """

    player1_wins = 0
    player2_wins = 0
    ties = 0
    total_moves_total = 0
    for _ in range(num_games):
        total_moves = 0
        game = Go(board_size, 2, True)
        while total_moves < 256:
            if game.done:
                break
            
            if game.turn == 1:
                if bot1 == "smart":
                    #print(f"smart1 {smart_bot(game, game.turn)}")
                    game.apply_move(smart_bot(game, game.turn))
                elif bot1 == "random":
                    #print(f"random1 {random_bot(game, game.turn)}")
                    game.apply_move(random_bot(game, game.turn))
                elif bot1 == "aggressive":
                   # print(f"aggressive1 {aggressive_bot(game, game.turn)}")
                    game.apply_move(aggressive_bot(game, game.turn))
                else:
                    sys.exit()
            else:
                if bot2 == "smart":
                    #print(f"smart2 {smart_bot(game, game.turn)}")
                    game.apply_move(smart_bot(game, game.turn))
                elif bot2 == "random":
                    #print(f"random2 {random_bot(game, game.turn)}")
                    game.apply_move(random_bot(game, game.turn))
                elif bot2 == "aggressive":
                    #print(f"aggressive2 {aggressive_bot(game, game.turn)}")
                    game.apply_move(aggressive_bot(game, game.turn))
                else:
                    sys.exit()
            total_moves += 1
        game.pass_turn()
        game.pass_turn()
        #print(f"p1 {game.scores()[1]}")
        #print(f"p2 {game.scores()[2]}")
        total_moves_total += total_moves
        outcome = game.outcome
        if outcome == [1]:
            player1_wins += 1
        elif outcome == [2]:
            player2_wins += 1
        else:
            ties += 1
        
    total_games = num_games
    player1_win_percentage = (player1_wins / total_games) * 100
    player2_win_percentage = (player2_wins / total_games) * 100
    tie_percentage = (ties / total_games) * 100
    average_moves = (total_moves_total/total_games)

    print(f"Player 1 wins: {player1_win_percentage:.2f}%")
    print(f"Player 2 wins: {player2_win_percentage:.2f}%")
    print(f"Ties: {tie_percentage:.2f}%")
    print(f"Average moves: {average_moves:.2f}")


if __name__ == "__main__":
    main()
