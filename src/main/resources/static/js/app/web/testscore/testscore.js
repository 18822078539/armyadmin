//@ sourceURL=testscore.js
$(function() {
    var $testscoreTableForm = $(".testscore-table-form");
    getType();
    var settings = {
        url: ctx + "user/listscore",
        pageSize: 10,
        clickToSelect: true,
        queryParams: function(params) {
            return {
                pageSize: params.limit,
                pageNum: params.offset / params.limit + 1,
                projectid:$("#projectid").val()
            };
        },
        columns: [{
            checkbox: true
        },{
            field: 'userId',
            title: '用户id'
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
                title: "成绩"
            },
            {
                field: "score1",
                title: "得分"
            },{
                field: 'evalute',
                title: '评价'
            },

        ],
        /**
         * @param {点击列的 field 名称} field
         * @param {点击列的 value 值} value
         * @param {点击列的整行数据} row
         * @param {td 元素} $element
         */
        onClickCell: function(field, value, row, $element) {
            if(field=="score"||field=="score1"){
                $element.attr('contenteditable', true);
                $element.unbind('blur').bind("blur",function () {
                    var index = $element.parent().data('index');
                    var tdValue = $element.html();
                    saveData(index, field, tdValue);
                });
            }

        }
    };

    $MB.initTable('testscoreTable', settings);
});

/**
 * 获取所有的测试类型
 */
function getType() {
    $.post(ctx + "testtype/getAll",{}, function (r) {
        var data = r.msg;
        var option = "<option value='0'>请选择</option>";
        for (var i = 0; i < data.length; i++) {
            option += "<option value='" + data[i].id + "'>" + data[i].name + "</option>"
        }
        $("#testscore").html("").append(option);
    });
    $("#testscore").on("change",function () {
        var type = $("#testscore").val();
        console.info("type="+type);
        $.post(ctx + "testproject/getByType",{typeId:type}, function (r) {
            var data = r.msg;
            var option = "<option value='0'>请选择</option>";
            for (var i = 0; i < data.length; i++) {
                option += "<option value='" + data[i].id + "'>" + data[i].name + "</option>"
            }
            $("#projectid").html("").append(option);
        });
    });

    $("#projectid").on("change",function () {
        $("#proId").val($("#projectid").val());
    });
}

function search() {
    $MB.refreshTable('testscoreTable');
}

function saveData(index, field, value) {
    console.info($("#projectid").val());
    if($("#projectid").val()==null){
        $MB.n_danger("请选择测试项目");
        return;
    }else {
        // var selected = $("#testscoreTable").bootstrapTable("getSelections");
        var selected = $("#testscoreTable").bootstrapTable('getData')
        var testscoreId = selected[index].userId;
        var str = "";
        // var testScore = {};
        // testScore.userid = testscoreId;
        // testScore.projectid = $("#projectid").val();
        if (field == "score1") {
            if (value == 0) {
                str = "缺考";
            } else if (value > 0 && value < 60) {
                str = "不合格";
            } else if (value >= 60 && value < 75) {
                str = "合格";
            } else if (value >= 75 && value < 90) {
                str = "良好";
            } else {
                str = "优秀";
            }
            // testScore.evalute = str;
            // testScore.score1 = value;
            $("#testscoreTable").bootstrapTable('updateCell', {
                index: index,       //行索引
                field: "evalute",       //列名
                value: str        //cell值
            })
            //更新评价和score1数据
            $.post(ctx + "testscore/update", {
                userId: testscoreId,
                projectId: $("#projectid").val(),
                score:$("#score").val(),
                score1: value,
                evalute: str

            }, function (r) {
                if (r.code === 0) {
                    // closeModal();
                    search();

                }
            });

        } else {
            $("#testscoreTable").bootstrapTable('updateCell', {
                index: index,       //行索引
                field: field,       //列名
                value: value        //cell值
            })
            $("#score").val(value);
            //更新score数据
        //     $.post(ctx + "testscore/update", {
        //         userId: testscoreId,
        //         projectId: $("#projectid").val(),
        //         score: value
        //     }, function (r) {
        //         if (r.code === 0) {
        //             // closeModal();
        //             search();
        //             // $MB.n_success(r.msg);
        //         }
        //     });
        //
        }
        // testScore.score = value;
    }

}
