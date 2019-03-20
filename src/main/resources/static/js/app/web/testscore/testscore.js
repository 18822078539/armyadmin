//@ sourceURL=testscore.js
$(function() {
    var $testscoreTableForm = $(".testscore-table-form");
    var settings = {
        url: ctx + "user/list",
        pageSize: 10,
        clickToSelect: true,
        queryParams: function(params) {
            return {
                pageSize: params.limit,
                pageNum: params.offset / params.limit + 1
            };
        },
        columns: [{
            checkbox: true
        }, {
            field: 'userId',
            visible: false
        }, {
            field: 'username',
            title: '用户名'
        }, {
            field: 'deptName',
            title: '单位'
        }, {
            field: 'email',
            title: '邮箱'
        }, {
            field: 'mobile',
            title: '手机'
        }, {
            field: 'crateTime',
            title: '创建时间'
        },
            {
                field: "score",
                title: "成绩",
                editable: {
                    type: 'text',
                    title: '成绩',
                    validate: function (v) {
                        if (!v) return '成绩不能为空';

                    }
                }
            },
            {
                field: "score1",
                title: "得分",
                editable: {
                    type: 'text',
                    title: '得分',
                    validate: function (v) {
                        if (!v) return '得分不能为空';

                    }
                }
            },{
                field: 'evalute',
                title: '评价'
            },

        ],
        onEditableSave: function (field, row, oldValue, $el) {
            $.ajax({
                type: "post",
                url: "/Editable/Edit",
                data: row,
                dataType: 'JSON',
                success: function (data, status) {
                    if (status == "success") {
                        alert('提交数据成功');
                    }
                },
                error: function () {
                    alert('编辑失败');
                },
                complete: function () {

                }

            });
        }
    };

    $MB.initTable('testscoreTable', settings);
});

function search() {
    $MB.refreshTable('testscoreTable');
}

function refresh() {
    $(".testscore-table-form")[0].reset();
    search();
}

function deletetestscores() {
    var selected = $("#testscoreTable").bootstrapTable('getSelections');
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
        $.post(ctx + 'testscore/delete', { "ids": ids }, function(r) {
            if (r.code === 0) {
                $MB.n_success(r.msg);
                refresh();
            } else {
                $MB.n_danger(r.msg);
            }
        });
    });
}

function exporttestscoreExcel(){
	$.post(ctx+"testscore/excel",$(".testscore-table-form").serialize(),function(r){
		if (r.code === 0) {
			window.location.href = "common/download?fileName=" + r.msg + "&delete=" + true;
		} else {
			$MB.n_warning(r.msg);
		}
	});
}

function exporttestscoreCsv(){
	$.post(ctx+"testscore/csv",$(".testscore-table-form").serialize(),function(r){
		if (r.code === 0) {
			window.location.href = "common/download?fileName=" + r.msg + "&delete=" + true;
		} else {
			$MB.n_warning(r.msg);
		}
	});
}