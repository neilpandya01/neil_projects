"""
Fake implementations of GoBase.

We provide a GoStub implementation, and you must
implement a GoFake implementation.
"""
from copy import deepcopy

from base import GoBase, BoardGridType, ListMovesType


class GoStub(GoBase):
    """
    Stub implementation of GoBase.

    This stub implementation behaves according to the following rules:

    - It only supports two players and boards of size 2x2 and above.

    - The board is always initialized with four pieces in the four
      corners of the board. Player 1 has pieces in the northeast and
      southwest corners of the board, and Player 2 has pieces in the
      southeast and northwest corners of the board.
    - Players are allowed to place pieces in any position of the board
      they want, even if there is already a piece in that position
      (placing a piece in such a position replaces the previous piece
      with the new one). The ko and superko rule do not apply.
    - The game ends after four moves. Whatever player has a piece in
      position (0,1) wins. If there is no piece in that position,
      the game ends in a tie.
    - The scores are always reported as 100 for Player 1 and 200 for
      Player 2. Note how the scores do not play a role in determining
      the outcome of the game.
    - It does not validate board positions. If a method is called with
      a position outside the board, the method will likely cause an exception.
    - It does not implement the load_game or simulate_moves method.
    """

    _grid: BoardGridType
    _turn: int
    _num_moves: int

    def __init__(self, side: int, players: int, superko: bool = False):
        """
        See GoBase.__init__
        """
        if players != 2:
            raise ValueError(
                "The stub implementation " "only supports two players"
            )

        super().__init__(side, players, superko)

        self._grid = [[None] * side for _ in range(side)]
        self._grid[0][-1] = 1
        self._grid[-1][0] = 1
        self._grid[0][0] = 2
        self._grid[-1][-1] = 2

        self._turn = 1
        self._num_moves = 0

    @property
    def grid(self) -> BoardGridType:
        """
        See GoBase.grid
        """
        return deepcopy(self._grid)

    @property
    def turn(self) -> int:
        """
        See GoBase.turn
        """
        return self._turn

    @property
    def available_moves(self) -> ListMovesType:
        """
        See GoBase.available_moves
        """
        moves = []
        for r in range(self._side):
            for c in range(self._side):
                moves.append((r, c))

        return moves

    @property
    def done(self) -> bool:
        """
        See GoBase.done
        """
        return self._num_moves == 4

    @property
    def outcome(self) -> list[int]:
        """
        See GoBase.outcome
        """
        if not self.done:
            return []

        if self._grid[0][1] is None:
            return [1, 2]
        else:
            return [self._grid[0][1]]

    def piece_at(self, pos: tuple[int, int]) -> int | None:
        """
        See GoBase.piece_at
        """
        r, c = pos
        return self._grid[r][c]

    def legal_move(self, pos: tuple[int, int]) -> bool:
        """
        See GoBase.legal_move
        """
        return True

    def apply_move(self, pos: tuple[int, int]) -> None:
        """
        See GoBase.apply_move
        """
        r, c = pos
        self._grid[r][c] = self._turn
        self.pass_turn()

    def pass_turn(self) -> None:
        """
        See GoBase.pass_turn
        """
        self._turn = 2 if self._turn == 1 else 1
        self._num_moves += 1

    def scores(self) -> dict[int, int]:
        """
        See GoBase.scores
        """
        return {1: 100, 2: 200}

    def load_game(self, turn: int, grid: BoardGridType) -> None:
        """
        See GoBase.load_game
        """
        raise NotImplementedError

    def simulate_move(self, pos: tuple[int, int] | None) -> "GoBase":
        """
        See GoBase.simulate_move
        """
        raise NotImplementedError


class GoFake (GoBase):
    _grid: BoardGridType
    _turn: int
    _num_moves: int

    def __init__(self, side: int, players: int, superko: bool = False):
        """
        See GoBase.__init__
        """
        if players != 2 or side < 4:
            raise ValueError("The GoFake implementation only supports two players or a board size greater than 4")

        super().__init__(side, players, superko)

        self._grid = [[None] * side for _ in range(side)]
        #self._grid[0][-1] = 1
        #self._grid[-1][0] = 1
        #self._grid[0][0] = 2
        #self._grid[-1][-1] = 2
        self._states = []
        self._zero_occupy = False
        self._consecutive_pass = 0

        self._turn = 1
        self._num_moves = 0

    @property
    def grid(self) -> BoardGridType:
        """
        See GoBase.grid
        """
        return deepcopy(self._grid)

    @property
    def turn(self) -> int:
        """
        See GoBase.turn
        """
        return self._turn

    @property
    def available_moves(self) -> ListMovesType:
        """
        See GoBase.available_moves
        """
        moves = []
        for r in range(self._side):
            for c in range(self._side):
                if self._grid[r][c] is None:
                    moves.append((r, c))
        
        return moves

    @property
    def done(self) -> bool:
        """
        See GoBase.done
        """
        if self.available_moves == []:
            return True
        if self._consecutive_pass == self._players:
            return True
        return False

    @property
    def outcome(self) -> list[int]:
        """
        See GoBase.outcome
        """
        if not self.done:
            return []
    
        scores = self.scores()
        winners: list[int] = []

        for key in scores:
            if scores[key] == max(scores.values()):
                winners.append(key)

        return winners

    def piece_at(self, pos: tuple[int, int]) -> int | None:
        """
        See GoBase.piece_at
        """
        #self.legal_move(pos)
        r, c = pos
        return self._grid[r][c]

    def legal_move(self, pos: tuple[int, int]) -> bool:
        """
        See GoBase.legal_move
        """
        temp_grid = self.simulate_move(pos)
        if not pos in self.available_moves:
            return False
        if self._superko:
            if temp_grid.grid in self._states:
                return False
        else:
            if len(self._states) > 1 and temp_grid.grid == self._states[-2]:
                return False

        return True

    def apply_move(self, pos: tuple[int, int]) -> None:
        """
        See GoBase.apply_move
        """
        r, c = pos
        if self.legal_move(pos) and pos != (0,0):
            #self._states.append(self.simulate_move(pos).grid)
            if r+1 < self.size and self.piece_at((r+1, c)) != self.turn:
                self._grid[r+1][c] = None
            if r-1 >= 0 and self.piece_at((r-1, c)) != self.turn:
                self._grid[r-1][c] = None
            if c+1 < self.size and self.piece_at((r, c+1)) != self.turn:
                self._grid[r][c+1] = None
            if c-1 >= 0 and self.piece_at((r, c-1)) != self.turn:
                self._grid[r][c-1] = None
            
            self._grid[r][c] = self._turn
            self._states.append(self.grid)
        if pos == (0, 0):
            self._zero_occupy = True
            for i, row in enumerate(self.grid):
                for j, col in enumerate(row):
                    if col is None:
                        self._grid[i][j] = self.turn
        else:
            self._num_moves += 1
            self.pass_turn()
            self._consecutive_pass = 0

    def pass_turn(self) -> None:
        """
        See GoBase.pass_turn
        """
        self._turn = 2 if self._turn == 1 else 1
        self._num_moves += 1
        self._consecutive_pass += 1

    def scores(self) -> dict[int, int]:
        """
        See GoBase.scores
        """
        result: dict[int, int] = {}

        for player in range(1, self._players + 1):
            result[player] = 0
        
        for row in self._grid:
            for col in row:
                if col is not None:
                    result[col] += 1
        
        return result

    def load_game(self, turn: int, grid: BoardGridType) -> None:
        """
        See GoBase.load_game
        """
        raise NotImplementedError

    def simulate_move(self, pos: tuple[int, int] | None) -> "GoBase":
        """
        See GoBase.simulate_move
        """
        if pos is None:
            new_game_state = GoFake(self.size, self._players, self._superko)
            new_game_state._grid = self.grid
            new_game_state._consecutive_pass = self._consecutive_pass
            #new_game_state._turn = 3 - self.turn  
            new_game_state.pass_turn()
            return new_game_state

        x, y = pos
        if not(0 <= x < self._side) or not (0 <= y < self._side):
            raise ValueError("Position is outside the bounds of the board")

        new_game_state = GoFake(self.size, self._players, self._superko)
        new_game_state._grid = self.grid
        new_game_state._grid[x][y] = self.turn
        new_game_state._turn = self.turn
        if x+1 < self.size and new_game_state.piece_at((x+1, y)) != self.turn:
            new_game_state._grid[x+1][y] = None
        if x-1 >= 0 and new_game_state.piece_at((x-1, y)) != self.turn:
            new_game_state._grid[x-1][y] = None
        if y+1 < self.size and new_game_state.piece_at((x, y+1)) != self.turn:
            new_game_state._grid[x][y+1] = None
        if y-1 >= 0 and new_game_state.piece_at((x, y-1)) != self.turn:
            new_game_state._grid[x][y-1] = None
        new_game_state.pass_turn()
        return new_game_state