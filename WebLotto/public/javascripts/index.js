$(document).ready(function() {
  $('.ui.form').form({
    fields: {
      kerberos : {
        identifier: 'kerberos',
        rules : [
          { type: 'empty',
            prompt: 'Please enter a valid kerberos'
          },
          {
            type: 'regExp[/^[a-z0-9]+$/i]',
            prompt: 'kerberos may not include any special characters, please input a valid kerberos'
          }
        ]
      }
    }
  });

  $('form').submit(function(e) {
    e.preventDefault();
    console.log("submitting kerberos");
    var k = $('input').val();
    $.post("/submit", {
      kerberos: k,
    }, function(data) {
      console.log(data)
      if (data === "success") {
        $('.ui.modal.successful').modal('show').modal('attach events', '.button.close', 'hide');
      } else if (data === "already in raffle") {
        $('.ui.modal.recorded').modal('show').modal('attach events', '.button.close', 'hide');
      } else if (data === "error") {
        $('.ui.modal.failed').modal('show').modal('attach events', '.button.close', 'hide');
      }
    });
    $('form').form('clear');
  });

  //kill modals on key press
}).on("keypress", function() {
  $('.ui.modal').modal('hide');
});
