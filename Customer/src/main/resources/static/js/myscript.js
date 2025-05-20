function redirectToPage(selectElement) {
    var selectedValue = selectElement.value;
    if (selectedValue === 'login') {
        window.location.href = '/login'; // Chuyển hướng đến trang đăng nhập
    } else if (selectedValue === 'register') {
        window.location.href = '/register'; // Chuyển hướng đến trang đăng ký
    }
}

function myFunction() {
    document.getElementById("demo").innerHTML = "Script chạy ổn";
}





