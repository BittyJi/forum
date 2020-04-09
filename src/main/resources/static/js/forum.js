//提交回复
function post() {
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();
    comment2target(questionId, 1, content)

}

function comment2target(targetId, type, content) {
    if (!content) {
        alert("输入的内容不能为空!");
        return;
    }
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: 'application/json',
        data: JSON.stringify({
            "parentId": targetId,
            "content": content,
            "type": type
        }),
        success: function (response) {
            if (response.code == 200) {
                window.location.reload();
            } else {
                if (response.code == 8001) {
                    var isAccepted = confirm(response.message);
                    if (isAccepted) {
                        window.open("https://github.com/login/oauth/authorize?client_id=5d6c648cae2767ddab1d&redirect_uri=http://localhost:8080/callback&scope=user&state=1");
                        window.localStorage.setItem("closable", true);
                    }
                } else {
                    alert(response.message);
                }

            }
            console.log(response.code);

        },
        dataType: "json"
    });

}

//二级评论
function comment(e) {
    var commentId = e.getAttribute("data-id");
    var content = $("#input-" + commentId).val();
    comment2target(commentId, 2, content);

}

// 展开二级评论
function collapseComments(e) {
    var id = e.getAttribute("data-id");
    var comments = $("#comment-" + id);
    if (comments.hasClass("in")) {
        comments.removeClass("in");
        e.classList.remove("active");
    } else {
        var subCommentContainer = $("#comment-" + id);
        if (subCommentContainer.children().length != 1) {
            comments.addClass("in");
            e.classList.add("active");
        } else {
            $.getJSON("/comment/" + id, function (data) {
                $.each(data.data, function (index, comment) {

                    var mediaLeftElement = $("<div/>", {
                        "class": "media-left",
                    }).append($("<img/>", {
                        "class": "media-object img-thumbnail",
                        "src": comment.user.avatarUrl
                    }));

                    var mediaBodyElement = $("<div/>", {
                        "class": "media-body",
                    }).append($("<h5/>", {
                        "class": "media-heading",
                        "html": comment.user.name
                    })).append($("<div/>", {
                        "html": comment.content
                    })).append($("<div/>", {
                        "class": "menu",
                    }).append($("<span/>", {
                        "class": "pull-right",
                        "html": moment(comment.gmtCreate).format('YYYY-MM-DD')
                    })));


                    var mediaElement = $("<div/>", {
                        "class": "media",
                    }).append(mediaLeftElement)
                        .append(mediaBodyElement);

                    var commentElement = $("<div/>", {
                        "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 comments"
                    }).append(mediaElement).append($("<hr/>"));
                    subCommentContainer.prepend(commentElement);
                });

                comments.addClass("in");
                e.classList.add("active");

            });
        }

    }

}

//判断邮箱是否存在
$(function isblur() {
    //为输入框绑定事件
    $("#email").blur(function () {
        //1失去焦点，获得表单输入数据
        //var usernameInput = $(username).val();
        var email = $("#email").val();
        var resubmit = $("#resubmit");
        //alert(usernameInput);
        //2去服务端校验该用户名是否存在--ajax将用户名提交到服务器校验
        $.ajax({
            type: "POST",
            url: "/registered/existEmail",
            contentType: 'application/json',
            data: JSON.stringify({
                "email": email
            }),
            success: function (response) {
                var usernameInfo = "";
                if (response.code == 200) {
                    if (email.indexOf("@") != -1 && email.length > email.indexOf("@") + 1) {
                        usernameInfo = "该邮箱可用";
                        resubmit.removeAttr("disabled");
                    }
                    else {
                        usernameInfo = "邮箱格式不正确";
                        resubmit.attr("disabled", 'disabled');
                    }

                    $("#usernameInfo").css("color", "green");
                } else {
                    usernameInfo = "该邮箱已经存在";
                    $("#usernameInfo").css("color", "red");
                    resubmit.attr("disabled", 'disabled');

                }
                $("#usernameInfo").html(usernameInfo);
                console.log(response.code);
            },
            dataType: "json"
        });

    });
});

function log() {
    var email = $("#loginEmail").val();
    var password = $("#loginPassword").val();

    $.ajax({
        type: "POST",
        url: "/login",
        contentType: 'application/json',
        data: JSON.stringify({
            "email": email,
            "userPwd": password
        }),
        success: function (response) {
            var usernameInfo = "";
            if (response.code == 200) {
                usernameInfo = response.message;
                $("#usernameInfo").css("color", "green");
                window.location.replace("/");
            }
            else {
                console.log(response.code);
                usernameInfo = response.message;
                $("#usernameInfo").css("color", "red");
            }
            $("#usernameInfo").html(usernameInfo);
            console.log(response.code);
        },
        dataType: "json"
    })
}

function yes() {

    alert("注册成功");
    window.location.replace("/");
}

function istrue() {
    var result = confirm("确定修改吗？");
    if (result == true) {
        return true;
    } else {
        return false;
    }
}


