from dist_comm_vis.adapter.unit_of_work import DefaultUnitOfWork


def main():
    DefaultUnitOfWork().service_model_application.create_for_project()
