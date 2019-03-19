var validator;
var $testtypeAddForm = $("#testtype-add-form");

$(function () {
    validateRule();

    $("#testtype-add .btn-save").click(function () {
        var name = $(this).attr("name");
        validator = $testtypeAddForm.validate();
        var flag = validator.form();
        if (flag) {
            if (name === "save") {
                $.post(ctx + "testtype/add", $testtypeAddForm.serialize(), function (r) {
                    if (r.code === 0) {
                        closeModal();
                        refresh();
                        $MB.n_success(r.msg);
                    } else $MB.n_danger(r.msg);
                });
            }
            if (name === "update") {
                $.post(ctx + "testtype/update", $testtypeAddForm.serialize(), function (r) {
                    if (r.code === 0) {
                        closeModal();
                        refresh();
                        $MB.n_success(r.msg);
                    } else $MB.n_danger(r.msg);
                });
            }
        }
    });

    $("#testtype-add .btn-close").click(function () {
        closeModal();
    });

});

function closeModal() {
    $("#testtype-add-button").attr("name", "save");
    $("#testtype-add-modal-title").html('新增测试类别');
    validator.resetForm();
    $MB.closeAndRestModal("testtype-add");
}

function validateRule() {
    var icon = "<i class='zmdi zmdi-close-circle zmdi-hc-fw'></i> ";
    validator = $testtypeAddForm.validate({
        rules: {
            code: {
                required: true,
                maxlength: 10
            },
            name: {
                required: true,
                maxlength: 10
            }
        },
        messages: {
            code: {
                required: icon + "请输入编号",
                maxlength: icon + "长度不能超过10个字符"
            },
            name: {
                required: icon + "请输入名称",
                maxlength: icon + "长度不能超过10个字符"
            }
        }
    });
}