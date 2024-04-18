let dataList = [];

function listAllUsers() {
    let httpRequest = new XMLHttpRequest();
    httpRequest.open("GET", '/admin/studentsData');
    httpRequest.send();
    httpRequest.addEventListener('readystatechange', function () {
        if (httpRequest.readyState === 4 && httpRequest.status === 200) {
            dataList = JSON.parse(httpRequest.responseText);
            display();
            console.log("Data received: ", dataList);
        }
    });
}

function display() {
    let box = '';
    for (let i = 0; i < dataList.length; i++) {
        box += `
             <tr>
                <td>${dataList[i].id}</td>
                <td>${dataList[i].name}</td>
                <td>${dataList[i].lastName}</td>
                <td>${dataList[i].degree}</td>
                <td>${dataList[i].roles.map(role => role.role).join(", ")}</td>
                <td>
                    <button onclick='editUser(${dataList[i].id})' class="btn btn-light px-3 edit btnEdit" data-bs-toggle="modal" data-bs-target="#exampleModal">Edit</button>
                    <button onclick='deleteUser(${dataList[i].id})' class="btn btn-danger btnDelete" data-bs-toggle="modal" data-bs-target="#deleteModal">Delete</button>
                </td>
             </tr>
        `;
    }
    document.getElementById('dataList').innerHTML = box;
}

function editUser(id) {
    let student = dataList.find(student => student.id === id);
    document.getElementById('editId').value = student.id;
    document.getElementById('editName').value = student.name;
    document.getElementById('editLastName').value = student.lastName;
    document.getElementById('editDegree').value = student.degree;
    // If you have a form for roles, you can populate it here
}

function saveEditedUser() {
    let id = document.getElementById('editId').value;
    let updatedStudent = {
        id: id,
        name: document.getElementById('editName').value,
        lastName: document.getElementById('editLastName').value,
        degree: document.getElementById('editDegree').value,
        // Add roles if needed
    };

    let httpRequest = new XMLHttpRequest();
    httpRequest.open("POST", `/admin/edit?id=${id}`);
    httpRequest.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    httpRequest.send(JSON.stringify(updatedStudent));
    httpRequest.addEventListener('load', function () {
        if (httpRequest.status === 200) {
            // Reload the list after successful update
            listAllUsers();
        } else {
            alert("Failed to update student. Please try again.");
        }
    });
}

function deleteUser(id) {
    if (confirm("Are you sure you want to delete this student?")) {
        let httpRequest = new XMLHttpRequest();
        httpRequest.open("POST", `/admin/delete?id=${id}`);
        httpRequest.send();
        httpRequest.addEventListener('load', function () {
            if (httpRequest.status === 200) {
                // Reload the list after successful deletion
                listAllUsers();
            } else {
                alert("Failed to delete student. Please try again.");
            }
        });
    }
}

document.addEventListener('DOMContentLoaded', function () {
    listAllUsers();
});
