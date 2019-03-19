$(function() {
    var $testprojectTableForm = $(".testproject-table-form");
    var settings = {
        url: ctx + "testproject/list",
        pageSize: 10,
        queryParams: function(params) {
            return {
                pageSize: params.limit,
                pageNum: params.offset / params.limit + 1,
                name: $testprojectTableForm.find("input[name='name']").val().trim(),
            };
        },
        columns: [{
                checkbox: true
            },
            {
                field: 'id',
                title: '测试项目ID',
                width: 150
            }, {
                field: 'name',
                title: '名称'
            }, {
                field: 'code',
                title: '编号'
            },{
                field: 'typename',
                title: '测试类型'
            },{
                field: 'testtime',
                title: '考核时间'
            },{
                field: 'person',
                title: '考核人员'
            }
        ]
    };

    $MB.initTable('testprojectTable', settings);
});

function search() {
    $MB.refreshTable('testprojectTable');
}

function refresh() {
    $(".testproject-table-form")[0].reset();
    search();
}

function deletetestprojects() {
    var selected = $("#testprojectTable").bootstrapTable('getSelections');
    var selected_length = selected.length;
    if (!selected_length) {
        $MB.n_warning('请勾选需要删除的测试项目！');
        return;
    }
    var ids = "";
    for (var i = 0; i < selected_length; i++) {
        ids += selected[i].id;
        if (i !== (selected_length - 1)) ids += ",";
    }
    $MB.confirm({
        text: "确定删除选中的测试项目？",
        confirmButtonText: "确定删除"
    }, function() {
        $.post(ctx + 'testproject/delete', { "ids": ids }, function(r) {
            if (r.code === 0) {
                $MB.n_success(r.msg);
                refresh();
            } else {
                $MB.n_danger(r.msg);
            }
        });
    });
}

function exporttestprojectExcel(){
	$.post(ctx+"testproject/excel",$(".testproject-table-form").serialize(),function(r){
		if (r.code === 0) {
			window.location.href = "common/download?fileName=" + r.msg + "&delete=" + true;
		} else {
			$MB.n_warning(r.msg);
		}
	});
}

function exporttestprojectCsv(){
	$.post(ctx+"testproject/csv",$(".testproject-table-form").serialize(),function(r){
		if (r.code === 0) {
			window.location.href = "common/download?fileName=" + r.msg + "&delete=" + true;
		} else {
			$MB.n_warning(r.msg);
		}
	});
}