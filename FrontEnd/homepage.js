const form = document.getElementById('user-amount-port-number-form');

const userAmountInputField = document.getElementById('user-amount-input-field');
const portNumInputField = document.getElementById('port-number-input-field');

const baseURL = 'pcvm3-10.instageni.illinois.edu';

const getRequest = (requestURl) => {
    return new Promise((resolve, reject) => {
        fetch(requestURl, {
            mode: 'cors',
        })
        .then(response => {
            if (response.ok) {
                return response.text();
            }
        })
        .then(body => {
            resolve(body);
        })
        .catch(err => {
            reject(err);
            alert('Connection Error: the port number is incorrect!');
        });
    })
}

const checkPassword = (password) => {
    let reg = /^[a-zA-Z]*$/;
    return reg.test(password);
}

const checkPortNumber = (portNumer) => {
    let reg = /^[0-9]*$/;
    return reg.test(portNumer);
}


form.onsubmit = (event) => {
    event.preventDefault();

    // clear previous forms
    const forms = document.querySelectorAll('.password-form');
    for (let i = 0; i < forms.length; i++) {
        document.body.removeChild(forms[i]);
    }
    
    const userNum = userAmountInputField.value;
    
    
    // create lists
    for (let i = 0; i < userNum; i++) {
        const form = document.createElement('form');
        form.className = 'password-form';
        document.body.appendChild(form);

        const userLabel = document.createElement('label');
        userLabel.className = 'user-id-label';
        userLabel.textContent = `User ${i}: `;

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

        form.appendChild(userLabel);
        form.appendChild(input);
        form.appendChild(submitBtn);
        form.appendChild(result);

        form.onsubmit = function(e) {
            e.preventDefault();
            result.textContent = 'Pending';
            const password = input.value;
            const portNum = portNumInputField.value;

            if (!checkPassword(password) || !(checkPortNumber(portNum))) {
                alert('Invalid value.');
            } else {
                const queryURL = `http://${baseURL}:${portNum}?key=${hex_md5(password)}&id=${i}`;

                let begin = new Date();
                getRequest(queryURL).then((res) => {
                    console.log(res);
                    let end = new Date();
                    result.textContent = `Result: ${res}, Runtime: ${(end-begin) / 1000} ms`;
                })
                
            }   
        }
    }
}

