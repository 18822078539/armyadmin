//@ sourceURL=scoresearch.js
$(function() {
    var $testscoreTableForm = $(".testscore-table-form");
    var settings = {
        url: ctx + "scoresearch/list",
        pageSize: 10,
        clickToSelect: true,
        queryParams: function(params) {
            return {
                pageSize: params.limit,
                pageNum: params.offset / params.limit + 1,
                username:$testscoreTableForm.find("input[name='username']").val().trim()
            };
        },
        columns: [{
            checkbox: true
        }, {
            field: 'username',
            title: '用户姓名'
        }, {
            field: 'typeName',
            title: '项目类别'
        }, {
            field: 'projectName',
            title: '测试项目'
        }, {
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
        // onClickCell: function(field, value, row, $element) {
        //     if(field=="score"||field=="score1"){
        //         $element.attr('contenteditable', true);
        //         $element.unbind('blur').bind("blur",function () {
        //             var index = $element.parent().data('index');
        //             var tdValue = $element.html();
        //             saveData(index, field, tdValue);
        //         });
        //     }
        //
        // }
    };

    $MB.initTable('testscoreTable', settings);
});


function search() {
    $MB.refreshTable('testscoreTable');
}

