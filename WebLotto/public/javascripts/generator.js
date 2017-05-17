$('.ui.button').click(function() {
  console.log("clicked");
  $.post("/generate", {
  }, function(data) {
    $('.result').text(data);
  });
});
