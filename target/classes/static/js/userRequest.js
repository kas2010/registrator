$(window).on('load', function () {
    if (!$("#userRoles").text().includes('ROLE_ADMIN')) {
        $(".for-admin").hide();
    }
    $('#preloader').fadeOut().end().delay(400).fadeOut('slow');
});

//Кнопка Добавить
$("#btnAddUserRequest").on('click', function (e) {
    e.preventDefault();
    $(".text-danger").remove();
    $("#frmUserRequestEdit").trigger('reset');
    $("#modalUserRequest").modal('show');
});

//Кнопка сохранить и выйти
$("#btnSaveAndClose").on('click', function (e) {
    e.preventDefault();
    if (validateForm() === false) return;
    if (!$("#userRoles").text().includes("ROLE_ADMIN")) {
        $("#state").val(1);
        $("#management").val($("#userManagement").text());
    }
    let formData = new FormData($('#frmUserRequestEdit')[0]);
    $.ajax({
        url: ctx + "userRequest/save",
        type: 'POST',
        processData: false,
        contentType: false,
        data: formData,
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
            $('#myModal .modal-body').text("Bad request to POST " + ctx + "userRequest");
            $('#myModal').modal('show')
        }
    });
});

//Валидация формы
function validateForm() {
    $(".text-danger").remove();

    let checking = true;

    //проверка статуса
    if (!$("#userRoles").text().includes('ROLE_ADMIN')) {
        if ($("#state").val() == 5) {
            $("#iin").after("<small class='form-text text-danger'>Нельзя редактировать удаленного пользователя</small>");
            $("#iin").focus();
            checking = false;
        }
    }

    if (checking === false) return false;

    //проверка на заполняемость поля
    if ($("#phone").val() === "") {
        $("#phone").after("<small class='form-text text-danger'>Поле обязательно для заполнения</small>");
        $("#phone").focus();
        checking = false;
    }
    if ($("#job").val() === "") {
        $("#job").after("<small class='form-text text-danger'>Поле обязательно для заполнения</small>");
        $("#job").focus();
        checking = false;
    }
    if ($("#degree").val() === "") {
        $("#degree").after("<small class='form-text text-danger'>Поле обязательно для заполнения</small>");
        $("#degree").focus();
        checking = false;
    }
    if ($("#department").val() === "") {
        $("#department").after("<small class='form-text text-danger'>Поле обязательно для заполнения</small>");
        $("#department").focus();
        checking = false;
    }
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
    if ($("#iin").val() === "") {
        $("#iin").after("<small class='form-text text-danger'>Поле обязательно для заполнения</small>");
        $("#iin").focus();
        checking = false;
    }

    if (checking === false) return false;

    //проверка длины ИИН и формата номера телефона
    if ($("#iin").val().length !== 12 || !($("#iin").val() > 0)) {
        $("#iin").after("<small class='form-text text-danger'>Поле должно состоять из 12 цифр</small>");
        $("#iin").focus();
        checking = false;
    }
    if ($("#phone").val().length !== 12 || !($("#phone").val() > 0) || !($("#phone").val().startsWith("+77"))) {
        $("#phone").after("<small class='form-text text-danger'>Поле должно начинаться с символов '+77' и состоять из 11 цифр</small>");
        $("#phone").focus();
        checking = false;
    }


    //проверка одновременной поставки пользователя на обнуление и удаление
    if ($("#resetPassword").prop('checked') && $("#deleteUser").prop('checked')) {
        $("#deleteUser").after("<small class='form-text text-danger'>Нельзя одновременно обнулить пароль и удалить пользователя. Выберите только одно из них.</small>");
        $("#deleteUser").focus();
        checking = false;
    }


    if (checking === false) return false;

    //проверка на уникальность ИИН
    $.ajax({
        async: false,
        url: ctx + 'userRequest/checkIin?iin=' + $("#iin").val() + '&dbTypeId=' + $("#dbType").val(),
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
                $("#iin").after("<small class='form-text text-danger'>Пользователь с таким ИИН уже существует</small>");
                $("#iin").focus();
                checking = false;
            }
        },
        error: function () {
            checking = false;
            $('#myModal .modal-title').text('Error');
            $('#myModal .modal-body').text("Bad request to GET " + ctx + 'userRequest/checkIin?iin=' + $("#iin").val());
            $('#myModal').modal('show');
        }
    });

    if (checking === false) return false;

    //проверка на уникальность ФИО
    $.ajax({
        async: false,
        url: ctx + 'userRequest/checkFIO?firstName=' + $("#firstName").val() +
            '&middleName=' + $("#middleName").val() +
            '&lastName=' + $("#lastName").val() +
            '&dbTypeId=' + $("#dbType").val(),
        type: 'GET',
        beforeSend: function () {
            let preload = document.getElementById('preloader');
            preload.style.display = "block";
        },
        complete: function () {
            $('#preloader').fadeOut().end().delay(400).fadeOut('slow');
        },
        success: function (data) {
            let cnt = id === "" ? 0 : 1;
            if (data > cnt) {
                $("#lastName").after("<small class='form-text text-danger'>Пользователь с таким ФИО уже существует</small>");
                $("#firstName").focus();
                checking = false;
            }
        },
        error: function () {
            checking = false;
            $('#myModal .modal-title').text('Error');
            $('#myModal .modal-body').text("Bad request to GET " + ctx + 'userRequest/checkFIO?firstName=' + $("#firstName").val() +
                '&middleName=' + $("#middleName").val() +
                '&lastName=' + $("#lastName").val());
            $('#myModal').modal('show');
        }
    });

    if (checking === false) return false;

    //проверка на уникальность сотового телефона
    $.ajax({
        async: false,
        url: ctx + 'userRequest/checkPhone?phone=' + $("#phone").val() + '&dbTypeId=' + $("#dbType").val(),
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
                $("#phone").after("<small class='form-text text-danger'>Пользователь с таким номером телефона уже существует</small>");
                $("#phone").val();
                checking = false;
            }
        },
        error: function () {
            checking = false;
            $('#myModal .modal-title').text('Error');
            $('#myModal .modal-body').text("Bad request to GET " + ctx + 'userRequest/checkPhone?phone=' + $("#phone").val());
            $('#myModal').modal('show');
        }
    });

    return checking;
}

function openUserRequest(id) {
    $.ajax({
        url: ctx + 'userRequest/get/' + id,
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
                if (data.hasOwnProperty('degree')) {
                    if (data.degree !== null) {
                        $('#degree option[value="' + data.degree.id + '"]').prop('selected', true);
                    }
                }
                if (data.hasOwnProperty('state')) {
                    if (data.state !== null) {
                        $('#state option[value="' + data.state.id + '"]').prop('selected', true);
                    }
                }
                if (data.hasOwnProperty('dbType')) {
                    if (data.dbType !== null) {
                        $('#dbType option[value="' + data.dbType.id + '"]').prop('selected', true);
                    }
                }
            });
            $('#modalUserRequest').modal('show');
        },
        error: function () {
            $('#myModal .modal-title').text('Error');
            $('#myModal .modal-body').text("Bad request to GET " + ctx + 'userRequest/get/' + id);
            $('#myModal').modal('show');
        }
    });
}

function deleteUserRequest(id) {
    $("#confirmModal modal-body").text("Удалить документ?");
    $("#btnYes").on('click', function () {
        $.ajax({
            url: ctx + 'userRequestDelete/' + id,
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
                $('#myModal .modal-body').text("Bad request to DELETE " + ctx + 'userRequestDelete/' + id);
                $('#myModal').modal('show');
            }
        });
    });
    $("#confirmModal").modal('show');
}

//Настройки для обычных пользователей
$('#modalUserRequest').on('show.bs.modal', function () {
    if (!$("#userRoles").text().includes('ROLE_ADMIN')) {
        $("#managementRecord").hide();
        $("#stateRecord").hide();
        $("#login").prop('readonly', true);
        $("#password").prop('readonly', true);
        if ($("#state").val() !== "2") {
            $("#login").hide();
            $("#password").hide();
        }
    }
});

//Кнопка Сформировать заявку
$("#btnPrintVedomost").on("click", function (e) {
    e.preventDefault();
    //формирование списка идентификаторов
    let checkboxes = document.getElementsByClassName('checkbox');
    let arr = [];
    for (let i = 0; i < checkboxes.length; i++) {
        if (checkboxes[i].checked) {
            arr.push(checkboxes[i].value);
        }
    }
    if (arr.length === 0) {
        alert("Ничего не выбрано");
        return;
    }
    $.ajax({
        url: ctx + "userRequest/printVedomost",
        type: 'GET',
        data: {ids: arr},
        beforeSend: function () {
            let preload = document.getElementById('preloader');
            preload.style.display = "block";
        },
        complete: function () {
            $('#preloader').fadeOut().end().delay(400).fadeOut('slow');
        },
        success: function (data) {
            $('#reportModal .modal-body').empty();
            $('#reportModal .modal-body').append("<p>Для открытия или сохранения документа перейдите по ссылке</p>" +
                "<a href='userRequest/downloadReport?fileName=" + data + "'>Ссылка на документ</a>");
            $('#reportModal').on('hidden.bs.modal', function (e) {
                e.preventDefault();
                setTimeout( function() {
                    $.ajax({
                        url: ctx + "userRequest/deleteReport?fileName=" + data,
                        type: 'DELETE',
                        error: function () {
                            $('#myModal .modal-title').text('Error');
                            $('#myModal .modal-body').text("Bad request to DELETE " + ctx + "userRequest/deleteReport?fileName=" + data);
                            $('#myModal').modal('show')
                        }
                    });
                }, 10000);
            });
            $('#reportModal').modal('show');
        },
        error: function () {
            $('#myModal .modal-title').text('Error');
            $('#myModal .modal-body').text("Bad request to GET " + ctx + "userRequest/printVedomost");
            $('#myModal').modal('show')
        }
    });
});

//Кнопка Печать
$("#btnPrintPassword").on("click", function (e) {
    e.preventDefault();
    //формирование списка идентификаторов
    let checkboxes = document.getElementsByClassName('checkbox');
    let arr = [];
    for (let i = 0; i < checkboxes.length; i++) {
        if (checkboxes[i].checked) {
            arr.push(checkboxes[i].value);
        }
    }
    if (arr.length === 0) {
        alert("Ничего не выбрано");
        return;
    }
    $.ajax({
        url: ctx + "userRequest/printPassword",
        type: 'GET',
        data: {ids: arr},
        beforeSend: function () {
            let preload = document.getElementById('preloader');
            preload.style.display = "block";
        },
        complete: function () {
            $('#preloader').fadeOut().end().delay(400).fadeOut('slow');
        },
        success: function (data) {
            $('#reportModal .modal-body').empty();
            $('#reportModal .modal-body').append("<p>Для открытия или сохранения документа перейдите по ссылке</p>" +
                "<a href='userRequest/downloadReport?fileName=" + data + "'>Ссылка на документ</a>");
            $('#reportModal').on('hidden.bs.modal', function (e) {
                e.preventDefault();
                setTimeout( function() {
                    $.ajax({
                        url: ctx + "userRequest/deleteReport?fileName=" + data,
                        type: 'DELETE',
                        error: function () {
                            $('#myModal .modal-title').text('Error');
                            $('#myModal .modal-body').text("Bad request to DELETE " + ctx + "userRequest/deleteReport?fileName=" + data);
                            $('#myModal').modal('show')
                        }
                    });
                }, 10000);
            });
            $('#reportModal').modal('show');
        },
        error: function () {
            $('#myModal .modal-title').text('Error');
            $('#myModal .modal-body').text("Bad request to GET " + ctx + "userRequest/printPassword");
            $('#myModal').modal('show')
        }
    });
});