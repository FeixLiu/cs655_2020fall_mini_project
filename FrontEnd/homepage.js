
let form = document.getElementById('password-form');
form.onsubmit = function (e) {//阻止冒泡
    if (e) {
      e.stopPropagation();
      e.preventDefault();
    }

    let inputField = document.getElementById('search-input-field');
    const keyword = inputField.value;
    document.getElementById('result').textContent = keyword;
  }
