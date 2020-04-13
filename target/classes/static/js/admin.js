$(window).on('load', function () {
    $('#preloader').fadeOut().end().delay(400).fadeOut('slow');
});

//Кнопка сохранить и выйти
$("#btnSaveAndClose").on('click', function (e) {
    e.preventDefault();
    $("#frmUserEdit").submit();
});

//Валидация формы
function validateForm() {
    $(".text-danger").remove();

    //проверка на заполняемость поля
    let checking = true;

    if ($("#management").val() === "") {
        $("#management").after("<small class='form-text text-danger'>Поле обязательно для заполнения</small>");
        $("#management").focus();
        checking = false;
    }
    if ($("#middleName").val() === "") {
        $("#middleName").after("<small class='form-text text-danger'>Поле обязательно для заполнения</small>");
        $("#middleName").focus();
        checking = false;
    }
    if ($("#firstName").val() === "") {
        $("#firstName").after("<small class='form-text text-danger'>Поле обязательно для заполнения</small>");
        $("#firstName").focus();
        checking = false;
    }
    if ($("#username").val() === "") {
        $("#username").after("<small class='form-text text-danger'>Поле обязательно для заполнения</small>");
        $("#username").focus();
        checking = false;
    }

    if (checking === false) return false;

    //Проверка на уникальность username
    $.ajax({
        async: false,
        url: ctx + 'users/checkUser?username=' + $("#username").val(),
        type: 'GET',
        beforeSend: function () {
            let preload = document.getElementById('preloader');
            preload.style.display = "block";
        },
        complete: function () {
            $('#preloader').fadeOut().end().delay(400).fadeOut('slow');
        },
        success: function (data) {
            let cnt = $("#id").val() === "" ? 0 : 1;
            if (data > cnt) {
                $("#username").after("<small class='form-text text-danger'>Пользователь с таким логином уже существует</small>");
                checking = false;
            }
        },
        error: function () {
            checking = false;
            $('#myModal .modal-title').text('Error');
            $('#myModal .modal-body').text("Bad request to GET " + ctx + 'users/checkUser?username=' + $("#username").val());
            $('#myModal').modal('show');
        }
    });

    if (checking === false) return false;

    //Проверка правильности ввода пароля
    if ($("#password").val() !== "" || $("#passwordReplay").val() !== "") {
        if ($("#password").val() !== $("#passwordReplay").val()) {
            $("#passwordReplay").after("<small class='form-text text-danger'>Пароли не совпадают</small>");
            $("#password").focus();
            checking = false;
        }
    }

    return checking;
}

function openUser(id) {
    $.ajax({
        url: ctx + 'users/get/' + id,
        type: 'GET',
        beforeSend: function () {
            let preload = document.getElementById('preloader');
            preload.style.display = "block";
        },
        complete: function () {
            $('#preloader').fadeOut().end().delay(400).fadeOut('slow');
        },
        success: function (data) {
            $.each(data, function (name, val) {
                let $el = $('#' + name), type = $el.attr('type');
                switch (type) {
                    case 'checkbox':
                        $el.prop('checked', val);
                        break;
                    case 'radio':
                        $el.filter('[value="' + val + '"]').attr('checked', 'checked');
                        break;
                    default:
                        $el.val(val);
                }
                if (data.hasOwnProperty('management')) {
                    if (data.management !== null) {
                        $('#management option[value="' + data.management.id + '"]').prop('selected', true);
                    }
                }
            });
            $('#modalUser').modal('show');
        },
        error: function () {
            $('#myModal .modal-title').text('Error');
            $('#myModal .modal-body').text("Bad request to GET " + ctx + 'users/get/' + id);
            $('#myModal').modal('show');
        }
    });
}

function deleteUser(id) {
    $("#btnYes").on('click', function () {
        $.ajax({
            url: ctx + 'users/delete/' + id,
            type: 'GET',
            beforeSend: function () {
                let preload = document.getElementById('preloader');
                preload.style.display = "block";
            },
            complete: function () {
                $('#preloader').fadeOut().end().delay(400).fadeOut('slow');
            },
            success: function () {
                location.reload();
            },
            error: function () {
                $('#myModal .modal-title').text('Error');
                $('#myModal .modal-body').text("Bad request to GET " + ctx + 'users/delete/' + id);
                $('#myModal').modal('show');
            }
        });
    });
    $("#confirmModal").modal('show');
}