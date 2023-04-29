import abc


class AbstractConfiguration(abc.ABC):
    LOG_FORMAT = 'LOG_FORMAT'
    LOG_LEVEL = 'LOG_LEVEL'

    @abc.abstractmethod
    def get_configuration_value(self, key: str) -> str:
        """Reads the configured value for the given key. Returns an empty string if not found"""
        raise NotImplementedError


class CommandlineConfiguration(AbstractConfiguration):
    def __init__(self, key_values: dict[str, str]):
        self.key_values = key_values

    def get_configuration_value(self, key: str) -> str:
        return self.key_values.get(key, '')
