import ntpath


class File:
    full_qualified_name: str
    extension: str

    def __init__(self, full_qualified_name: str):
        self.full_qualified_name = full_qualified_name
        # removes the leading "." from the extension
        self.extension = ntpath.splitext(full_qualified_name)[1][1:]
