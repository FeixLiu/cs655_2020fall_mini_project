const portNumForm = document.getElementById('port-number-form');
const portNumInputField = document.getElementById('port-number-input-field');
const addUserBtn = document.getElementById('add-button');
const BASE_URL = 'pcvm3-8.instageni.cenic.net';
const PASSWORD_LEN = 5;

let userNum = 0;
let portNum;

/**
 * Check if the password is valid
 * @param {string} password
 */
function checkPassword(password) {
    let reg = /^[a-zA-Z]*$/;
    return reg.test(password) && (password !== '');
}

/**
 * Check if the port number is valid
 * @param {number} portNumer 
 */
function checkPortNumber(portNumer) {
    let reg = /^[0-9]*$/;
    return reg.test(portNumer);
}

/**
 * Generate a random password
 * @param {number} len the length of the password
 */
function randomString(len) {
    let t = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz",
    res = "";
    for (let i = 0; i < len; i++) {
        res += t.charAt(Math.floor(Math.random() * t.length));
    }
    return res;
}

/**
 * Handle HTTP request of the url
 * @param {string} requestURl 
 */
function getRequest(requestURl) {
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
            alert('Connection refused, please change a port number.');
        });
    })
}

/**
 * The response function when click the "submit" button
 * @param {string} password 
 * @param {number} portNum 
 * @param {number} userID 
 * @param {Element} resultEle 
 * @param {Element} inputEle 
 */
function submitPassword(password, portNum, userID, resultEle, inputEle) {
    if (!checkPassword(password) || !(checkPortNumber(portNum))) {
        alert('Invalid value.');
    } else {
        const md5Password = hex_md5(password);
        const queryURL = `http://${BASE_URL}:${portNum}?key=${md5Password}&id=${userID}`;
        let startTime = new Date();
        resultEle.textContent = 'Pending';
        inputEle.disabled = true;
        getRequest(queryURL).then((res) => {
            console.log(res);
            let endTime = new Date();
            if(res.length === 5){
                resultEle.textContent = `Result: ${res}. Runtime: ${(endTime-startTime) / 1000} s`;
            }
            else{
                alert('Internal Server Error!');
                resultEle.textContent = '';
            }
            inputEle.disabled = false;
        })
    }
}

/**
 * The response function when click "remove" button
 * @param {number} userID 
 */
function removeUserForm(userID) {
    const form = document.getElementById(`form-${userID}`);
    document.body.removeChild(form);
}

/**
 * Create a form for an added user.
 * @param {number} userID 
 */
function createUserForm(userID) {
    const form = document.createElement('form');
    form.id = `form-${userID}`
    form.className = 'password-form';
    
    const userLabel = document.createElement('label');
    userLabel.className = 'user-id-label';
    userLabel.textContent = `User ${userID}: `;

    const input = document.createElement('input');
    input.type = 'text';
    input.className = 'password-input-field';
    input.placeholder = `${PASSWORD_LEN}-character password`;
    input.maxLength = PASSWORD_LEN;
    input.minLength = PASSWORD_LEN;

    const submitBtn = document.createElement('button');
    submitBtn.className = 'submit-button';
    submitBtn.type = 'submit';
    submitBtn.textContent = 'Submit';

    const randomBtm = document.createElement('button');
    randomBtm.className = 'random-button';
    randomBtm.type = 'button';
    randomBtm.textContent = 'Random';
    

    const result = document.createElement('label');
    result.id = `password-crack-result-${userID}`;
    result.className = 'password-crack-result';

    const removeBtn = document.createElement('button');
    removeBtn.className = 'remove-button';
    removeBtn.textContent = 'Remove';
    removeBtn.type = 'button';
    removeBtn.onclick = () => {
        if (result.textContent !== 'Pending') {
            removeUserForm(userID);
        } else {
            alert('The cracking is running!');
        }
    }

    randomBtm.onclick = () => {
        if (result.textContent !== 'Pending') {
            input.value = randomString(PASSWORD_LEN);
        } else {
            alert('The cracking is running!');
        }
    }

    form.appendChild(userLabel);
    form.appendChild(input);
    form.appendChild(randomBtm);
    form.appendChild(submitBtn);
    form.appendChild(removeBtn);
    form.appendChild(result);
    
    form.onsubmit = function(e) {
        e.preventDefault();
        const password = input.value;
        if (result.textContent !== 'Pending') {
            submitPassword(password, portNum, userID, result, input);
        } else {
            alert('The cracking is running!');
        }
    }
    return form;
}

addUserBtn.onclick = () => {
    const newForm = createUserForm(userNum);
    document.body.appendChild(newForm);
    userNum++;
}

portNumForm.onsubmit = (event) => {
    event.preventDefault();

    // Clear forms
    const forms = document.querySelectorAll('.password-form');
    for (let i = 0; i < forms.length; i++) {
        document.body.removeChild(forms[i]);
    }

    portNum = portNumInputField.value;
    
    if (portNum === '') {
        alert('Please input a port number.');
    } else {
        const queryURL = `http://${BASE_URL}:${portNum}?key=connection-check`;

        // Only when the port number is available, next steps are available.
        getRequest(queryURL).then(() =>{
            addUserBtn.style.visibility = "visible";
        }).catch(() => {
            addUserBtn.style.visibility = "hidden";
        })
    }
}
    

