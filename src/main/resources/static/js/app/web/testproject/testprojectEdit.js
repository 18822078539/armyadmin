function updatetestproject() {
    var selected = $("#testprojectTable").bootstrapTable("getSelections");
    var selected_length = selected.length;
    if (!selected_length) {
        $MB.n_warning('请勾选需要修改的测试项目！');
        return;
    }
    if (selected_length > 1) {
        $MB.n_warning('一次只能修改一个测试项目！');
        return;
    }
    var testprojectId = selected[0].id;
    $.post(ctx + "testproject/gettestproject", {"testprojectId": testprojectId}, function (r) {
        if (r.code === 0) {
            var $form = $('#testproject-add');
            $form.modal();
            var testproject = r.msg;
            $("#testproject-add-modal-title").html('修改测试项目');
            $form.find("input[name='id']").val(testproject.id);
            $form.find("input[name='name']").val(testproject.name);
            $form.find("input[name='code']").val(testproject.code);
            $form.find("input[name='des']").val(testproject.des);
            $("#testproject-add-button").attr("name", "update");
        } else {
            $MB.n_danger(r.msg);
        }
    });
}