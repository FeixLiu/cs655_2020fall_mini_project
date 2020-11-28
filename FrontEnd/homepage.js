const form = document.getElementById('user-amount-form');
const inputField = document.getElementById('user-amount-input-field');
const baseURL = 'pcvm3-10.instageni.illinois.edu';

const getRequest = (requestURl) => {
    return new Promise((resolve, reject) => {
        fetch(requestURl)
        .then(response => {
            if (response.ok) {
                return response.json();
            }
        })
        .then(body => {
            resolve(body);
        })
        .catch(err => {
            reject(err);
        });
    })
}

const checkPassword = (password) => {
    let reg = /^[a-zA-Z]*$/;
    return reg.test(password);
}

form.onsubmit = (event) => {
    event.preventDefault();

    // clear previous forms
    const forms = document.querySelectorAll('.password-form');
    for (let i = 0; i < forms.length; i++) {
        document.body.removeChild(forms[i]);
    }
    
    const userNum = inputField.value;
    
    // create lists
    for (let i = 0; i < userNum; i++) {
        const form = document.createElement('form');
        form.className = 'password-form';
        document.body.appendChild(form);

        const input = document.createElement('input');
        input.type = 'text';
        input.className = 'password-input-field';
        input.placeholder = '5-character password';
        input.maxLength = 5;
        input.minLength = 5;

        const submitBtn = document.createElement('button');
        submitBtn.className = 'submit-button';
        submitBtn.textContent = 'submit';

        const result = document.createElement('label');
        result.className = 'password-crack-result';
        result.textContent = `Result: `;

        form.appendChild(input);
        form.appendChild(submitBtn);
        form.appendChild(result);

        form.onsubmit = function(e) {
            e.preventDefault();
            const password = input.value;

            if (!checkPassword(password)) {
                alert('Password must be characters.');
            } else {
                const queryURL = `http://${baseURL}:58111?key=${password}&id=${i}`;
                getRequest(queryURL).then((res) => {
                    console.log(res + "success!");
                })
            }
        }
    }
}

