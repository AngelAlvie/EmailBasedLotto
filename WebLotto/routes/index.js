var express = require('express');
var router = express.Router();
var fs = require('fs');

var path = 'data/kerberoes.txt';

//this function is an endpoint
var addKerberos = function(res, kerberos) {
  fs.appendFile(path, kerberos + '\n', function(err) {
    if (err) {
      console.error(err);
      res.send('error');
      throw err;
    } else {
      console.log('file read success: ' + kerberos + ' added to file');
      res.send('success');
    }
  });
};

/* GET home page. */
router.get('/', function(req, res, next) {
  res.render('index', { title: 'ProjXpo Raffle' });
});

router.get('/generator', function(req, res, next) {
  res.render('generator', {title: 'ProjXpo Winner Generator'})
});

router.post('/submit', function(req, res, next) {
  var kerberos = req.body.kerberos.toLowerCase();
  console.log("checking kerberos: " + kerberos);
  fs.readFile(path, 'utf8', function(err, data) {
    if (err) {
      console.error(err);
      res.send('error');
      throw err;
    } else {
      data = data.split('\n');
      data.pop();
      console.log(data.length + "kerberoes recorded");
      if (/^[a-z0-9]+$/.test(kerberos)) {
        for (var i = 0; i < data.length; i++) {
          console.log(data[i]);
          if (data[i] === kerberos) {
              res.send("already in raffle");
              return;
          }
        }
        addKerberos(res, kerberos);
      } else {
        console.log("kerberos denied");
        res.send("not valid character");
      }

    }
  });
});

router.post('/generate', function(req, res, next) {
  fs.readFile(path, 'utf8', function(err, data) {
    if (err) {
      console.error(err);
      res.send('internal server error');
      throw err;
    } else {
      data = data.split('\n');
      data.pop();
      console.log(data.length + " kerberoes pulled from for generate request");
      var lotto = Math.floor(Math.random()*data.length);
      console.log("lucky number " + lotto + " selected, with kerberos: " + data[lotto]);
      res.send(data[lotto]);
    }
  });
});

module.exports = router;
