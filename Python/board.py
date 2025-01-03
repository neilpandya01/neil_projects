from typing import Optional
from copy import deepcopy

class Board:
    '''
    Class representing a board object that can be used in any game
    that uses grids, and any games that place pieces on these grids
    '''
    _grid: list[list[int | None]]
    _size: int
    _previous_states: list[list[list[int | None]]]

    def __init__(self, size: int):
        '''
        Constructor

        Inputs:
            size [int]: size of the square board
        '''
        self._grid = [[None] * size for _ in range(size)]
        self._size = size
        self._previous_states = []

    def remove_piece (self, pos: tuple[int, int]):
        for i in pos:
            if i < 0 or i >= self._size:
                raise ValueError
        self._grid[pos[0]][pos[1]] = None
        #self._previous_states = [self._grid]

    
    @property
    def grid(self) -> list[list[int | None]]:
        '''
        Returns the current state of the board
        '''
        return deepcopy(self._grid)
    

    @property
    def size(self) -> int:
        '''
        Returns size of the board
        '''
        return self._size
    
    @property
    def previous_states(self) -> list[list[list[int | None]]]:
        '''
        Returns all of the previous states of the board
        '''
        return self._previous_states
    
    def set(self, pos: tuple[int, int], piece: int | None) -> None:
        '''
        Sets a piece on the board given a position

        Inputs:
            pos [tuple[int, int]]: poisiton that the piece should be placed at
            piece [int | None]: the piece to be placed

        Return [None]
        '''
        for i in pos:
            if i < 0 or i >= self._size:
                raise ValueError
        self._grid[pos[0]][pos[1]] = piece
        self._previous_states.append(self._grid)
    
    def get_piece(self, pos: tuple[int, int]) -> int | None:
        '''
        Returns the piece at the given position

        Inputs:
            pos [tuple[int, int]]: position that we are checking

        Returns [int | None]: Value of piece 
        '''
        row, col = pos
        return self._grid[row][col]
    
    def count(self, piece: int | None) -> int:
        '''
        Counts the amount of a given piece on the board

        Inputs:
            piece [int | None]: piece to be counted
        
        Returns [int]: the number of the given piece on the board
        '''
        counter = 0
        for row in self._grid:
            for col in row:
                if col == piece:
                    counter += 1
        return counter
    
    def clear_history(self) -> None:
        '''
        Resets the previous states list. 

        Inputs: none

        Returns: none
        '''
        self._previous_states = []
    
    def add_to_state(self) -> None:
        '''
        Adds the current grid state to previous states
        ''' 
        self._previous_states.append(self.grid)