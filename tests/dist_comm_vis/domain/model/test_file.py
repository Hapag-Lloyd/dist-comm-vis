from dist_comm_vis.domain.model.File import File


def test_extension_is_present_after_init():
    # given
    given_full_qualified_name = "my-file.java"

    # when
    actual_file = File(given_full_qualified_name)

    # then
    assert actual_file.extension == "java"
