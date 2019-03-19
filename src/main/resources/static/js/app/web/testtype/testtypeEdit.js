function updatetesttype() {
    var selected = $("#testtypeTable").bootstrapTable("getSelections");
    var selected_length = selected.length;
    if (!selected_length) {
        $MB.n_warning('请勾选需要修改的测试类别！');
        return;
    }
    if (selected_length > 1) {
        $MB.n_warning('一次只能修改一个测试类别！');
        return;
    }
    var testtypeId = selected[0].id;
    $.post(ctx + "testtype/gettesttype", {"testtypeId": testtypeId}, function (r) {
        if (r.code === 0) {
            var $form = $('#testtype-add');
            $form.modal();
            var testtype = r.msg;
            $("#testtype-add-modal-title").html('修改测试类别');
            $form.find("input[name='id']").val(testtype.id);
            $form.find("input[name='name']").val(testtype.name);
            $form.find("input[name='code']").val(testtype.code);
            $form.find("input[name='des']").val(testtype.des);
            $("#testtype-add-button").attr("name", "update");
        } else {
            $MB.n_danger(r.msg);
        }
    });
}