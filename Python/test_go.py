import pytest
from go import Go
ListMovesType = list[tuple[int, int]]
BoardGridType = list[list[int | None]]

#
# MILESTONE 1
#

def test_properties() -> None:
    """
    Test the size, num_players, and turn properties, as well as 
    the piece_at, legal_move, and available_moves methods.
    """
    game = Go(19, 2)
    assert game.size == 19
    assert game.num_players == 2
    assert game.turn == 1
    assert game.piece_at((0, 0)) is None
    assert game.legal_move((0, 0))
    for row in range(19):
        for col in range(19):
            assert (row, col) in game.available_moves


def test_make_move() -> None:
    """
    Verify the piece_at method as well as turn, done, and outcome
    properties after a move is made.
    """
    game = Go(19, 2)
    game.apply_move((0, 0))
    assert game.piece_at((0, 0)) == 1
    assert game.turn == 2
    assert not game.done
    assert game.outcome == []


def test_game_end() -> None:
    """
    Verify that the grid contains the expected pieces, and that
    done and outcome return values consistent with a game 
    that has ended after a few non-capture moves are made.
    """
    game = Go(19, 2)
    
    grid = [[None]*19 for _ in range(19)]
    for i in range(4):
        grid[i][i] = (i % 2) + 1

    game.load_game(1, grid)
    game.pass_turn()
    game.pass_turn()

    assert game.piece_at((0, 0)) == 1
    assert game.piece_at((1, 1)) == 2
    assert game.piece_at((2, 2)) == 1
    assert game.piece_at((3, 3)) == 2

    assert game.done
    assert game.outcome == []


#
# MILESTONE 2
#
    
def basic_properties(rows: int, players: int) -> None:
    """
    Test the size, num_players, and turn properties, as well as 
    the piece_at, legal_move, and available_moves methods with
    a given board size and number of players.

    Inputs:
        rows [int]: size of Go board
        players [int]: number of players
    
    Returns nothing
    """
    game = Go(rows, players)
    assert game.size == rows
    assert game.num_players == players
    assert game.turn == 1
    assert game.piece_at((0, 0)) is None
    assert game.legal_move((0, 0))
    for row in range(rows):
        for col in range(rows):
            assert (row, col) in game.available_moves


def setup_game(game: Go, moves: list[tuple[int, int, int]], final_turn: int):
    """
    Set up the board using the load_game function.

    Inputs:
        game (Go): the game to be edited
        moves (list(tuple(int, int))): moves to execute
        final_turn (int): the turn of the next player
    
    Returns:
        game (Go): the updated go game
    """
    grid = [[None]*19 for _ in range(19)]
    for row, col, piece in moves:
        grid[row][col] = piece
    
    game.load_game(final_turn, grid)

    return game


def test_9x9_properties() -> None:
    """
    Test basic properties for a 9x9 board.
    """
    basic_properties(9, 2)


def test_13x13_properties() -> None:
    """
    Test basic properties for a 13x13 board.
    """
    basic_properties(13, 2)


def test_legal_and_available() -> None:
    """
    Verify that legal_move and available_moves allow moves in
    any position of the board.
    """
    game = Go(19, 2)
    available_moves = game.available_moves
    for row in range(19):
        for col in range(19):
            assert game.legal_move((row, col))
            assert (row, col) in available_moves


def test_pieces_placed() -> None:
    """
    Verify that placing pieces places them in the
    Board and that the turn updates correctly.
    """
    game = Go(19, 2)
    game.apply_move((1, 1))
    assert game.turn == 2
    game.apply_move((2, 2))
    assert game.turn == 1

    assert game.piece_at((1, 1)) == 1
    assert game.piece_at((2, 2)) == 2


def test_legality() -> None:
    """
    Make a move, and verify that the position of
    that move is no longer a legal move.
    """
    game = Go(19, 2)
    game.apply_move((1, 1))
    assert not game.legal_move((1, 1))


def test_pass() -> None:
    """
    Verify that the turn upates correctly after passing.
    """
    game = Go(19, 2)
    game.apply_move((1, 1))
    game.pass_turn()
    assert game.turn == 1


def test_end_after_pass() -> None:
    """
    Verify the game ends after both players pass.
    """
    game = Go(19, 2)
    game.apply_move((1, 1))
    game.pass_turn()
    game.pass_turn()
    assert game.done


def test_single_capture() -> None:
    """
    Make moves that will result in one piece being
    captured. Verify that the piece is indeed captured.
    """
    moves = [(1, 2, 1), (2, 1, 1), (2, 3, 1), (2, 2, 2)]
    game = setup_game(Go(19, 2), moves, 1)

    game.apply_move((3, 2))
    assert game.piece_at((2, 2)) is None


def test_multi_capture() -> None:
    """
    Make moves that will result in multiple pieces being captured.
    Verify that all the pieces were indeed captured.
    """
    moves = [
        (2, 2, 2),
        (2, 1, 1),
        (2, 3, 2),
        (3, 1, 1),
        (3, 2, 2),
        (2, 4, 1),
        (3, 3, 2)
    ]
    for i in range(1, 5):
        moves.append((1, i, 1))
        moves.append((4, i, 1))

    game = setup_game(Go(19, 2), moves, 1)
    game.apply_move((3, 4))

    assert game.piece_at((2, 2)) is None
    assert game.piece_at((2, 3)) is None
    assert game.piece_at((3, 2)) is None
    assert game.piece_at((3, 3)) is None


def test_ko() -> None:
    """
    Test the simple ko rule (move cannot create the board from the prior move). 
    """
    moves = [(1, 2, 1), (2, 1, 1), (2, 3, 1), (3, 2, 1)]
    game = setup_game(Go(19, 2), moves, 2)
    
    assert not game.legal_move((2, 2))


def test_superko() -> None:
    """
    Test the superko rule.
    """
    moves = [
        (0, 1, 1),
        (0, 2, 2),
        (1, 0, 1),
        (1, 3, 2),
        (1, 2, 1),
        (2, 2, 2),
        (2, 1, 1),
        (3, 2, 2),
        (3, 1, 1),
        (4, 1, 2),
        (4, 0, 1),
        (4, 3, 2),
        (5, 1, 1),
        (5, 2, 2),
        (1, 1, 1),
        (4, 2, 2),
        (1, 2, 2)
    ]
    game = setup_game(Go(19, 2, True), moves, 1)
    assert not game.legal_move((4, 1))


def zero_scores_moves() -> Go:
    """
    Implements a set of moves that results in no territories. 
    """
    moves = []
    for i in range(9):
        moves.append((i, i, i%2 + 1))
    return setup_game(Go(19, 2), moves, 1)


def scores_moves() -> Go:
    """
    Implements a set of moves that results in player 1
    having a territory size 4 and player 2 a territory
    size 1.
    """
    moves = [
        (0, 0, 1),
        (0, 6, 2),
        (0, 1, 1),
        (1, 7, 2),
        (0, 2, 1),
        (1, 5, 2),
        (1, 0, 1),
        (2, 6, 2),
        (1, 2, 1),
        (2, 0, 1),
        (2, 2, 1),
        (3, 0, 1),
        (3, 2, 1),
        (4, 0, 1),
        (4, 2, 1)
    ]
    return setup_game(Go(19, 2), moves, 2)


def test_zero_scores() -> None:
    """
    Test that each player's score is just the
    number of pieces when no territories are made.
    """
    game = zero_scores_moves()
    scores = list(game.scores().values())

    assert scores[0] == 5
    assert scores[1] == 4


def test_scores() -> None:
    """
    Test the scores when the players have
    territories of size 4 and 1.
    """
    game = scores_moves()
    scores = list(game.scores().values())

    assert scores[0] == 11
    assert scores[1] == 5


def test_zero_scores_outcome() -> None:
    """
    Test that player one wins when they
    have more pieces on the board.
    """
    game = zero_scores_moves()
    for i in range(2):
        game.pass_turn()
    
    assert game.outcome == [1]


def test_scores_outcome() -> None:
    """
    Test that player 1 wins when their
    territory is bigger than 2's. 
    """
    game = scores_moves()
    for i in range(2):
        game.pass_turn()
    
    assert game.outcome == [1]


def test_three_player_moves() -> None:
    """
    Verify that pieces get placed and turn updates as
    moves are made in a 3 player game.
    """
    game = Go(19, 3)
    for i in range(1, 4):
        game.apply_move((i, i))
        assert game.turn == (i % game.num_players) + 1
        assert game.piece_at((i, i)) == i


def test_three_player_end() -> None:
    """
    Test that the game ends in a 3 player game
    when they all pass.
    """
    game = Go(19, 3)
    game.apply_move((1, 1))
    for __ in range(3):
        game.pass_turn()
    
    assert game.done


def test_grid_1() -> None:
    """
    Check that grid for an empty game is exported correctly
    """
    game = Go(19, 2)
    grid = game.grid

    for row in range(game.size):
        for col in range(game.size):
            assert grid[row][col] is None


def test_grid_2() -> None:
    """
    Check that grid returns a deep copy of the board's grid,
    and that modifying grid's return value doesn't modify
    the game's board
    """
    game = Go(19, 2)
    grid = game.grid

    grid[5][5] = 1
    assert game.piece_at((5, 5)) is None, (
        "grid() returned a shallow copy of the game's board. "
        "Modifying the return value of grid() should not "
        "affect the game's board."
    )


def test_grid_3() -> None:
    """
    Check that grid returns a correct copy of the board after making
    a few moves (none of the moves will result in a capture)
    """
    moves = [
        (3, 3, 1),
        (6, 16, 2),
        (1, 1, 1),
        (13, 0, 2),
        (16, 1, 1),
        (18, 15, 2),
        (13, 14, 1),
        (2, 10, 2),
        (1, 17, 1),
        (3, 13, 2),
        (11, 2, 1),
        (2, 8, 2),
        (13, 11, 1),
        (11, 0, 2),
        (4, 17, 1),
        (3, 6, 2),
        (16, 2, 1),
        (5, 2, 2),
        (14, 8, 1),
        (12, 2, 2),
    ]
    game = setup_game(Go(19, 2), moves, 1)
    grid = game.grid

    for row in range(game.size):
        for col in range(game.size):
            assert grid[row][col] == game.piece_at((row, col))


def test_simulate_move_1() -> None:
    """
    Test that simulating a move creates a new game
    """
    game = Go(19, 2)
    new_game = game.simulate_move((5, 5))

    # Check that the original Go object has not been modified
    assert game.piece_at((5, 5)) is None
    assert game.turn == 1

    # Check that the move was applied in the new Go object
    assert new_game.piece_at((5, 5)) == 1
    assert new_game.turn == 2


def test_simulate_move_2() -> None:
    """
    After making a few moves, check that simulating a move
    correctly creates a new game.
    """
    moves = [
        (3, 3, 1),
        (6, 16, 2),
        (1, 1, 1),
        (13, 0, 2),
        (16, 1, 1),
        (18, 15, 2),
        (13, 14, 1),
        (2, 10, 2),
        (1, 17, 1),
        (3, 13, 2),
        (11, 2, 1),
        (2, 8, 2),
        (13, 11, 1),
        (11, 0, 2),
        (4, 17, 1),
        (3, 6, 2),
        (16, 2, 1),
        (5, 2, 2),
        (14, 8, 1),
        (13, 2, 2),
    ]
    game = setup_game(Go(19, 2), moves, 1)
    new_game = game.simulate_move((5, 5))

    # Check that the original Go object has not been modified
    assert game.piece_at((5, 5)) is None
    for row, col, __ in moves:
        assert game.piece_at((row, col)) is not None
    assert game.turn == 1

    # Check that the move was applied in the new Go object
    assert new_game.piece_at((5, 5)) == 1
    for row, col, __ in moves:
        assert new_game.piece_at((row, col)) is not None
    assert new_game.turn == 2


def test_simulate_move_3() -> None:
    """
    We place one piece in position (5, 6) and then
    simulate placing pieces in (5, 7), (5, 5), (4, 6),
    and (6, 6). The piece in position (5, 6) should be 
    captured (but only in the new game created by simulate_move).
    """
    game = Go(19, 2)

    game.apply_move((5, 6))
    new_game = (game.simulate_move((5, 7)).simulate_move(None).
                simulate_move((5,5)).simulate_move(None).
                simulate_move((4, 6)).simulate_move(None).simulate_move((6, 6)))

    # Check that the original Go object has not been modified
    assert game.piece_at((5, 6)) == 1
    assert game.piece_at((5, 7)) is None
    assert game.turn == 2

    # Check that the move was applied in the new Go object
    assert new_game.piece_at((5, 6)) is None
    assert new_game.piece_at((5, 5)) == 2
    assert new_game.turn == 1


def test_simulate_move_4() -> None:
    """
    Check that simulating a pass works correctly.
    """
    game = Go(19, 2)
    new_game = game.simulate_move(None)

    # Check that the original Go object has not been modified
    assert game.turn == 1

    # Check that the pass was applied in the new Go object
    assert new_game.turn == 2


def test_simulate_move_5() -> None:
    """
    Check that simulating two consecutive passes works correctly.
    """
    game = Go(19, 2)
    new_game = game.simulate_move(None).simulate_move(None)

    # Check that the original Go object has not been modified
    assert game.turn == 1
    assert not game.done

    # Check that the passes were applied in the new Go object
    assert new_game.done


def test_capture_10_pieces() -> None:
    """
    Test the capture of 10 pieces.
    """
    moves = [
        (1, 0, 2),
        (2, 0, 2),
        (1, 6, 2),
    ]
    for row in range(1, 3):
        for col in range(1, 6):
            moves.append((row, col, 1))
    
    for i in range(1, 6):
        moves.append((0, i, 2))
        moves.append((3, i, 2))
    
    game = setup_game(Go(19, 2), moves, 2)
    game.apply_move((2, 6))

    for row in range(1, 3):
        for col in range(1, 6):
            assert game.piece_at((row, col)) is None


def test_neutral_territory() -> None:
    """
    Test that the scores for each player are correct
    when a neutral territory is present.
    """
    moves = [
        (0, 1, 1),
        (0, 2, 1),
        (0, 3, 1),
        (1, 0, 1),
        (1, 4, 1),
        (2, 1, 1),
        (2, 2, 1),
        (2, 3, 1),
        (3, 0, 2),
        (3, 4, 2),
        (4, 1, 2),
        (4, 2, 2),
        (4, 3, 2),
        (5, 1, 2),
        (5, 3, 2),
        (6, 2, 2),
    ]
    game = setup_game(Go(19, 2), moves, 1)

    scores = list(game.scores().values())
    assert scores[0] == 12
    assert scores[1] == 9


def test_3player_neutral_territory() -> None:
    """
    Test that the scores for each player are correct
    in a 3-player game when a neutral territory is present.
    """
    moves = [
        (0, 2, 1),
        (1, 1, 1),
        (1, 3, 1),
        (2, 2, 1),
        (2, 4, 2),
        (3, 3, 2),
        (3, 5, 2),
        (4, 4, 2),
        (4, 2, 3),
        (5, 1, 3),
        (5, 3, 3),
        (6, 2, 3)
    ]
    game = setup_game(Go(19, 3), moves, 1)
    
    scores = list(game.scores().values())
    assert scores[0] == 5
    assert scores[1] == 5
    assert scores[2] == 5