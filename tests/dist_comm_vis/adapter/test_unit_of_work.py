from dist_comm_vis.adapter.unit_of_work import DefaultUnitOfWork


def test_should_initialize_all_services_when_enter():
    with DefaultUnitOfWork() as uow:
        assert uow.service_model_application is not None
