from base import GoBase
from board import Board
from typing import Optional
BoardGridType = list[list[int | None]]
ListMovesType = list[tuple[int, int]]


class Go(GoBase):
    """
    Class for the game Go.
    """
    _side: int
    _players: int
    _superko: bool


    def __init__(self, side: int, players: int, superko: bool = False):
        super().__init__(side, players, superko)
        self._board = Board(self._side)
        self._consecutive_pass = 0
        self._turn = 1


    @property
    def grid(self) -> BoardGridType:
        """
        See GoBase.grid
        """
        return self._board.grid


    @property
    def size(self) -> int:
        """
        See GoBase.size
        """
        return self._side


    @property
    def num_players(self) -> int:
        """
        See GoBase.num_players
        """
        return self._players


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
                if self._board.get_piece((r, c)) is None:
                    moves.append((r, c))
       
        return moves


    @property
    def done(self) -> bool:
        """
        See GoBase.done
        """
        if self.available_moves == []:
            return True
        if self._consecutive_pass >= self._players:
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

        if len(winners) > 1:
            return []
        return winners

    @property
    def legal_moves_list(self) -> ListMovesType:
        """
        see GoBase.legal_moves_list
        """
        moves = []
        for r in range(self._side):
            for c in range(self._side):
                if (self._board.get_piece((r, c)) is None) and \
                    (self.legal_move((r, c))):
                    moves.append((r, c))
        return moves
    #
    # METHODS
    #


    def piece_at(self, pos: tuple[int, int]) -> int | None:
        """
        See GoBase.piece_at
        """
        return self._board.get_piece(pos)


    def legal_move(self, pos: tuple[int, int]) -> bool:
        """
        See GoBase.legal_move
        """
        if not pos in self.available_moves:
            return False
        temp_grid = self.simulate_move(pos)
        #
        if self._superko:
            if temp_grid.grid in self._board.previous_states:
                return False
        else:
            if (len(self._board.previous_states) > 1 and
                    temp_grid.grid == self._board.previous_states[-2]):
                """idk why but for some reason it adds an extra to the end 
                    of the list, thats why its -3, and not -2"""
                return False
        return True            

    def remove_pieces(self, positions: list[tuple[int, int]]) -> None:
        pieces_to_delete = []
        for position in positions:
            piece = self._board.get_piece(position)
            if piece is not None and piece != self.turn:
                pieces_to_delete += self.captured(position)
        for piece in pieces_to_delete:
            self._board.remove_piece(piece)


    def apply_move(self, pos: tuple[int, int]) -> None:
        """
        See GoBase.apply_move
        """
        if pos is None:
            self.pass_turn()
            self._consecutive_pass += 1
        elif self.legal_move(pos): 
           self._board.set(pos, self.turn)
           self.remove_pieces(self.neighbors(pos))
           if self.captured(pos) != []:
               self._board.remove_piece(pos)
           self._board.add_to_state()
           self.pass_turn()
           self._consecutive_pass = 0

   
    def captured(self, 
                 pos: tuple[int, int], 
                 visited: Optional[set[tuple[int, int]]] =
                 None) -> list[tuple[int, int]]:
        '''
        Determines all the coordinates where a capture can be made.


        Inputs:
            pos: [tuple[int, int]]: base position
            visited: Optional[set[tuple[int, int]]]

        Return list[tuple[int, int]] = all the positions to be removed
        '''
        if visited is None:
            visited = set(pos)
        p_captured = [pos]
        positions = self.neighbors(pos)
        current_piece = self.piece_at(pos)
        for position in positions:
            if self._board.get_piece(position) is None:
                return []
            elif self._board.get_piece(position) == current_piece:
                if not position in visited:
                    visited.add(position)
                    future_captured = self.captured(position, visited)
                    if future_captured == []:
                        return []
                    p_captured += future_captured
        return p_captured
    

    def pass_turn(self) -> None:
        """
        See GoBase.pass_turn
        """
        if self._turn == self._players:
            self._turn = 1
        else:
            self._turn += 1
        self._consecutive_pass += 1


    def scores(self) -> dict[int, int]:
        """
        See GoBase.scores
        """  
        result: dict[int, int] = {}

        for player in range(1, self._players + 1):
            result[player] = 0
        
        for idx, row in enumerate(self._board.grid):
            for idx2, col in enumerate(row):
                if col is not None:
                    result[col] += 1
                else:
                    territory = self.territory((idx, idx2))
                    if territory is not None:
                        result[territory] += 1
        return result


    def load_game(self, turn: int, grid: BoardGridType) -> None:
        """
        See GoBase.load_game
        """
        if turn > self._players or turn < 1:
            raise ValueError
       
        if len(grid) > self._side or len(grid[0]) > self._side:
            raise ValueError
       
        for row in grid:
            for element in row:
                if isinstance(element, int):
                    if element > self._players:
                        raise ValueError


        self._turn = turn
        self._board = Board(self._side)


        for y, row in enumerate(grid):
            for x, element in enumerate(row):
                self._board.set((y, x), element)
       
        self._board.clear_history()
         


    def simulate_move(self, pos: tuple[int, int] | None) -> "GoBase":
        """
        See GoBase.simulate_move
        """
        if pos is None:
            new_game_state = Go(self.size, self._players, self._superko)
            new_game_state._board._grid = self.grid
            new_game_state._consecutive_pass = self._consecutive_pass
            new_game_state._turn = self.turn
            new_game_state.pass_turn()
            return new_game_state


        x, y = pos
        if not(0 <= x < self._side) or not (0 <= y < self._side):
            raise ValueError("Position is outside the bounds of the board")


        new_game_state = Go(self.size, self._players, self._superko)
        new_game_state._board._grid = self.grid
        new_game_state._turn = self.turn
        new_game_state._board.set(pos, new_game_state.turn)
        new_game_state.remove_pieces(new_game_state.neighbors(pos))
        if new_game_state.captured(pos) != []:
            new_game_state._board.remove_piece(pos)
            #look back here
        new_game_state._board.add_to_state()
        new_game_state.pass_turn()
        return new_game_state

    
    def neighbors(self, pos: tuple[int, int]) -> list[tuple[int, int]]:
        """
        inputs: tuple[int, int]
        outputs:
        list of tuples of all the neighbors of a position
        """
        lst: list[tuple[int, int]] = []
        x, y = pos
        if (x+1)<self._side :
            lst.append((x+1,y))
        if (y+1)<self._side:
            lst.append((x,y+1))
        if (y-1)>=0:
            lst.append((x,y-1))
        if (x-1)>=0:
            lst.append((x-1,y))
        return lst

    def territory(self, pos: tuple[int, int], player: Optional[int] = None, 
                  checked: Optional[set[tuple[int, int]]] = None) -> Optional[int]:
        """
        inputs: position
        output: optional int of which player's territory a square belongs to 
        """
        if checked is None:
            checked = set()
        checked.add(pos)
        not_done = True
        for neighbor in self.neighbors(pos):
            if not neighbor in checked and not_done is not None:
                neighbor_piece = self._board.get_piece(neighbor)
                if player is None and neighbor_piece is not None:
                    player = neighbor_piece
                    checked.add(neighbor)
                elif (player is not None and neighbor_piece is not None and 
                        player != neighbor_piece):
                    return None
                if neighbor_piece is None:
                    player = self.territory(neighbor, player, checked)
                    not_done = player
        return player

        

