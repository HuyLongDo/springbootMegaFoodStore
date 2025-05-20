$('document').ready(function () {
    $('table #editButton').on('click', function (event) {
        event.preventDefault();
        var href = $(this).attr('href');
        $.get(href, function (category, status) {
            $('#idEdit').val(category.id);
            $('#nameEdit').val(category.name);
        });
        $('#editModal').modal();
    });
});

function checkCategoryActivated(activated) {
    if (activated == false) {
        alert('Category đang vô hiệu hóa, không thể cập nhật hoặc xóa');
        return false;
    }
    return true;
}