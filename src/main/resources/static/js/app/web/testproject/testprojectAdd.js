var validator;
var $testprojectAddForm = $("#testproject-add-form");

$(function () {
    validateRule();
    //获取所有的测试类别
    getAllType();
    getTimePicker();
    $("#testproject-add .btn-save").click(function () {
        var name = $(this).attr("name");
        validator = $testprojectAddForm.validate();
        var flag = validator.form();
        if (flag) {
            if (name === "save") {
                $.post(ctx + "testproject/add", $testprojectAddForm.serialize(), function (r) {
                    if (r.code === 0) {
                        closeModal();
                        refresh();
                        $MB.n_success(r.msg);
                    } else $MB.n_danger(r.msg);
                });
            }
            if (name === "update") {
                $.post(ctx + "testproject/update", $testprojectAddForm.serialize(), function (r) {
                    if (r.code === 0) {
                        closeModal();
                        refresh();
                        $MB.n_success(r.msg);
                    } else $MB.n_danger(r.msg);
                });
            }
        }
    });

    $("#testproject-add .btn-close").click(function () {
        closeModal();
    });

});

/**
 * 获取所有测试类别
 */
function getAllType() {
    $.post(ctx + "testtype/getAll",{}, function (r) {
        var data = r.msg;
        var option = "<option value='0'>请选择</option>";
        for (var i = 0; i < data.length; i++) {
            option += "<option value='" + data[i].id + "'>" + data[i].name + "</option>"
        }
        $("#typeid").html("").append(option);
    });
}

function getTimePicker() {
    $('input[name="datepicker"]').daterangepicker({
        "autoApply": true, //选择日期后自动提交;只有在不显示时间的时候起作用timePicker:false
        singleDatePicker: true, //单日历
        showDropdowns: true, //年月份下拉框
        timePicker: true, //显示时间
        timePicker24Hour: true, //时间制
        timePickerSeconds: true, //时间显示到秒
        startDate: moment().hours(0).minutes(0).seconds(0), //设置开始日期
        // maxDate: moment(new Date()), //设置最大日期
        "opens": "center",
        showWeekNumbers: true,
        locale: {
            format: "YYYY-MM-DD HH:mm:ss", //设置显示格式
            applyLabel: '确定', //确定按钮文本
            cancelLabel: '取消', //取消按钮文本
            daysOfWeek: ['日', '一', '二', '三', '四', '五', '六'],
            monthNames: ['一月', '二月', '三月', '四月', '五月', '六月',
                '七月', '八月', '九月', '十月', '十一月', '十二月'
            ],
            firstDay: 1
        },
    }, function(start) {
        console.log(start.format('YYYY-MM-DD HH:mm:ss'));
        $("#testtime").val(start.format('YYYY-MM-DD HH:mm:ss'));
    });
}

function closeModal() {
    $("#testproject-add-button").attr("name", "save");
    $("#testproject-add-modal-title").html('新增测试项目');
    validator.resetForm();
    $MB.closeAndRestModal("testproject-add");
}

function validateRule() {
    var icon = "<i class='zmdi zmdi-close-circle zmdi-hc-fw'></i> ";
    validator = $testprojectAddForm.validate({
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