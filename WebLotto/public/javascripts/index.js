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
      console.log(data);
    })
  });
});
