"use strict";

var portNumForm = document.getElementById('port-number-form');
var portNumInputField = document.getElementById('port-number-input-field');
var addUserBtn = document.getElementById('add-button');
var BASE_URL = 'pcvm3-10.instageni.illinois.edu';
var userNum = 0;
var portNum;

function checkPassword(password) {
  var reg = /^[a-zA-Z]*$/;
  return reg.test(password);
}

function checkPortNumber(portNumer) {
  var reg = /^[0-9]*$/;
  return reg.test(portNumer);
}

function getRequest(requestURl) {
  return new Promise(function (resolve, reject) {
    fetch(requestURl, {
      mode: 'cors'
    }).then(function (response) {
      if (response.ok) {
        return response.text();
      }
    }).then(function (body) {
      resolve(body);
    })["catch"](function (err) {
      reject(err);
      alert('Connection Refused');
    });
  });
}

function submitPassword(password, portNum, userID, resultEle) {
  if (!checkPassword(password) || !checkPortNumber(portNum)) {
    alert('Invalid value.');
  } else {
    var md5Password = hex_md5(password);
    var queryURL = "http://".concat(BASE_URL, ":").concat(portNum, "?key=").concat(md5Password, "&id=").concat(userID);
    var startTime = new Date();
    resultEle.textContent = 'Pending';
    getRequest(queryURL).then(function (res) {
      console.log(res);
      var endTime = new Date();
      resultEle.textContent = "Result: ".concat(res, ". Runtime: ").concat((endTime - startTime) / 1000, " s");
    });
  }
}

function removeUserForm(userID) {
  var form = document.getElementById("form-".concat(userID));
  document.body.removeChild(form);
}

function createUserForm(userID) {
  var form = document.createElement('form');
  form.id = "form-".concat(userID);
  form.className = 'password-form';
  var userLabel = document.createElement('label');
  userLabel.className = 'user-id-label';
  userLabel.textContent = "User ".concat(userID, ": ");
  var input = document.createElement('input');
  input.type = 'text';
  input.className = 'password-input-field';
  input.placeholder = '5-character password';
  input.maxLength = 5;
  input.minLength = 5;
  var submitBtn = document.createElement('button');
  submitBtn.className = 'submit-button';
  submitBtn.type = 'submit';
  submitBtn.textContent = 'submit';
  var result = document.createElement('label');
  result.id = "password-crack-result-".concat(userID);
  result.className = 'password-crack-result';
  var removeBtn = document.createElement('button');
  removeBtn.className = 'remove-button';
  removeBtn.textContent = 'Remove';
  removeBtn.type = 'button';

  removeBtn.onclick = function () {
    if (result.textContent !== 'Pending') {
      removeUserForm(userID);
    } else {
      alert('The cracking is running!');
    }
  };

  form.appendChild(userLabel);
  form.appendChild(input);
  form.appendChild(submitBtn);
  form.appendChild(removeBtn);
  form.appendChild(result);

  form.onsubmit = function (e) {
    e.preventDefault();
    var password = input.value;
    submitPassword(password, portNum, userID, result);
  };

  return form;
}

addUserBtn.onclick = function () {
  var newForm = createUserForm(userNum);
  document.body.appendChild(newForm);
  userNum++;
};

portNumForm.onsubmit = function (event) {
  event.preventDefault(); // clear previous forms

  var forms = document.querySelectorAll('.password-form');

  for (var i = 0; i < forms.length; i++) {
    document.body.removeChild(forms[i]);
  }

  portNum = portNumInputField.value;
  var md5Password = hex_md5("aaaaa");
  var queryURL = "http://".concat(BASE_URL, ":").concat(portNum, "?key=").concat(md5Password, "&id=").concat(-1);
  getRequest(queryURL).then(function () {
    addUserBtn.style.visibility = "visible";
  })["catch"](function () {
    addUserBtn.style.visibility = "hidden";
  });
};