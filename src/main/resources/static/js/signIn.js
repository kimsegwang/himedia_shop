
$(document).ready(() => {

    $('#signin').click(() => {

        let userId = $('#user_id').val();
        let password = $('#password').val();

        let formData = {
            username : userId,
            password : password,
        }


        $.ajax({
            type: 'POST',
            url: '/login',
            data: $.param(formData),
            success: (response) => {
                console.log('res :: ', response)
                if (response.loggedIn) {
                    window.location.href = response.url;
                }

                alert(response.message);
            },
            error: (error)=> {
                alert(error.responseJSON.message);
                window.location.href = error.responseJSON.url;
            }
        });

    });



});