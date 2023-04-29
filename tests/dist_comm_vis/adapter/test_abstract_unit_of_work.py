import pytest

from dist_comm_vis.adapter.configuration import CommandlineConfiguration
from dist_comm_vis.base_test_methods import TestUnitOfWork


def test_should_return_the_configuration_value_for_a_key():
    # given
    unit_of_work = TestUnitOfWork(CommandlineConfiguration({'LOG_LEVEL': 'value'}))

    # when
    actual_value = unit_of_work.get_configuration_value_for('LOG_LEVEL')

    # then
    assert actual_value == "value"


def test_should_return_the_configuration_value_for_a_key_if_multiple_configurations_are_in_place():
    # given
    unit_of_work = TestUnitOfWork(CommandlineConfiguration({'LOG_LEVEL': 'value'}))
    unit_of_work.add_configuration(CommandlineConfiguration({'LOG_FORMAT': 'value1'}))

    # when
    actual_value = unit_of_work.get_configuration_value_for('LOG_FORMAT')

    # then
    assert actual_value == "value1"


def test_should_return_the_configuration_value_for_the_first_configuration_if_the_key_exists_in_multiple_configurations():
    # given
    unit_of_work = TestUnitOfWork(CommandlineConfiguration({'LOG_LEVEL': 'value'}))
    unit_of_work.add_configuration(CommandlineConfiguration({'LOG_FORMAT': 'value1'}))

    # when
    actual_value = unit_of_work.get_configuration_value('LOG_LEVEL')

    # then
    assert actual_value == "value"


def test_should_add_the_configuration_values():
    # given
    unit_of_work = TestUnitOfWork(CommandlineConfiguration({'LOG_LEVEL': 'value'}))

    # when
    actual_value = unit_of_work.get_configuration_value('LOG_FORMAT')

    # then
    assert actual_value == ""

    # when
    unit_of_work.add_configuration(CommandlineConfiguration({'LOG_FORMAT': 'value1'}))

    actual_value = unit_of_work.get_configuration_value('LOG_FORMAT')

    # then
    assert actual_value == "key1"


def test_should_raise_an_error_if_the_configuration_key_is_unknown():
    # given
    unit_of_work = TestUnitOfWork(CommandlineConfiguration({'LOG_LEVEL': 'value'}))

    # when
    with pytest.raises(ValueError) as exception_info:
        unit_of_work.get_configuration_value('UNKNOWN')
