from enum import Enum


class Direction(Enum):
    """Either it's an incoming connection or an outgoing connection."""
    IN = "in"
    OUT = "out"
