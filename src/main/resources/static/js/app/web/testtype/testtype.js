$(function() {
    var $testtypeTableForm = $(".testtype-table-form");
    var settings = {
        url: ctx + "testtype/list",
        pageSize: 10,
        queryParams: function(params) {
            return {
                pageSize: params.limit,
                pageNum: params.offset / params.limit + 1,
                name: $testtypeTableForm.find("input[name='name']").val().trim(),
            };
        },
        columns: [{
                checkbox: true
            },
            {
                field: 'id',
                title: '测试类别ID',
                width: 150
            }, {
                field: 'name',
                title: '名称'
            }, {
                field: 'code',
                title: '编号'
            }
        ]
    };

    $MB.initTable('testtypeTable', settings);
});

function search() {
    $MB.refreshTable('testtypeTable');
}

function refresh() {
    $(".testtype-table-form")[0].reset();
    search();
}

function deletetesttypes() {
    var selected = $("#testtypeTable").bootstrapTable('getSelections');
    var selected_length = selected.length;
    if (!selected_length) {
        $MB.n_warning('请勾选需要删除的测试类别！');
        return;
    }
    var ids = "";
    for (var i = 0; i < selected_length; i++) {
        ids += selected[i].id;
        if (i !== (selected_length - 1)) ids += ",";
    }
    $MB.confirm({
        text: "确定删除选中的测试类别？",
        confirmButtonText: "确定删除"
    }, function() {
        $.post(ctx + 'testtype/delete', { "ids": ids }, function(r) {
            if (r.code === 0) {
                $MB.n_success(r.msg);
                refresh();
            } else {
                $MB.n_danger(r.msg);
            }
        });
    });
}

function exporttesttypeExcel(){
	$.post(ctx+"testtype/excel",$(".testtype-table-form").serialize(),function(r){
		if (r.code === 0) {
			window.location.href = "common/download?fileName=" + r.msg + "&delete=" + true;
		} else {
			$MB.n_warning(r.msg);
		}
	});
}

function exporttesttypeCsv(){
	$.post(ctx+"testtype/csv",$(".testtype-table-form").serialize(),function(r){
		if (r.code === 0) {
			window.location.href = "common/download?fileName=" + r.msg + "&delete=" + true;
		} else {
			$MB.n_warning(r.msg);
		}
	});
}