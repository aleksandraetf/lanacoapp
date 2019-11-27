

export function checkIfLogged() {
    console.log("Provjera")
    let resp = false;
    resp = fetch('/isAuthenticated/',
        {
            method: 'GET',
            mode: 'cors',
            headers:
            {
                credentials: 'include'
            },
        }
    ).then(request => { return request.status === 200; })
        .catch();
    return resp;
}