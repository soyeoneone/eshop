$(document).ready(function () {
    jQuery.validator.addMethod("isMobile",function (value,element) {
        var tel =/^1[35789]\d{9}/;
        return tel.test(value);
    });
    //登陆页面表单校验规则
    $("#login_form").validate({
        errorPlacement: function(error, element) {
            // Append error within linked label
            error.appendTo(element.parent().parent())
        },
        errorElement: "span",
        rules:{
            username:{
                required:true,
                minlength:5
            },
            password:{
                required:true,
                minlength:6
            }
        },
        messages:{

        }
    });
    //注册页面表单校验规则
    $("#reg_form").validate({
        //错误信息位置自定义
        errorPlacement: function(error, element) {
            // Append error within linked label
            error.appendTo(element.parent().parent())
            error.addClass("radio-inline")
        },
        errorElement: "span",
        //规则
        rules:{
            username:{
                required:true,
                minlength:5
            },
            password:{
                required:true,
                minlength:6
            },
            repassword:{
                equalTo:"#inputPassword3"
            },
            telephone:{
                required:true,
                isMobile:true
            },
            email:{
                required:true,
                email:true
            },
            name:"required",
            sex:"required",
            birthday:{
                required:true,
                date:true
            },
            verifycode:{}
        },
        messages:{
            telephone:{
                required:"这是必填字段",
                isMobile:"请输入有效的手机号码"
            }
        }
    })
})
