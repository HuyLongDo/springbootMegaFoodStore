// //Xử lý load số lượng món trong giỏ
// $(document).ready(function() {
//     $('.quantityBoxSpecial .qteSpecial').on('change', function() {
//         var productId = $(this).closest('.quantityBoxSpecial').siblings('input[name="id"]').val();
//         var quantity = $(this).val();
//         updateQuantity(productId, quantity);
//     });
// });
//
// function updateQuantity(productId, quantity) {
//     $.ajax({
//         url: '/shop/update-cart',
//         type: 'POST',
//         data: {
//             quantity: quantity,
//             id: productId,
//         },
//         success: function(response) {
//             // Xử lý phản hồi từ server (nếu cần)
//             // Ví dụ: Cập nhật tổng giá trị giỏ hàng, hiển thị thông báo, vv.
//         },
//         error: function(xhr, status, error) {
//             // Xử lý lỗi (nếu cần)
//         }
//     });
// }

$(document).ready(function() {
    $('.quantityBoxSpecial input[type="number"]').on('change', function() {
        var form = $(this).closest('form');
        form.submit();
    });
});