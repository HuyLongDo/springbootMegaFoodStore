<H3>1. Dự án cá nhân</H3>
Website cửa hàng bánh ngọt sử dụng java springboot, mình chọn framework này vì thấy được nhiều mặt thuận tiện triển khai cả cấu trúc dự án và có ý tưởng này trong quá trình thực tập doanh nghiệp, càng tìm hiểu về cách vận hành quản lý dự án giúp cho bản thân mình định hình được các vai trò khi phát triển website.

<H3>2. Giới thiệu</H3>
&nbsp; Về web cửa hàng bánh, có phạm vi hoạt động cho loại cửa hàng nhỏ tư nhân, cấu trúc tổ chức dự án theo mô hình mvc web service truyền thống. Có thể tùy biến phát triển thêm chức năng theo yêu cầu của người dùng muốn mở rộng.<br>
Nghiệp vụ:<br>
&nbsp; Đối với khách hàng là những người có nhu cầu mua bánh, họ có thể xem, tìm kiếm các thông tin về các loại bánh, mua và đặt hàng một cách nhanh chóng, tiện lợi không ràng buộc về thời gian hay đi lại.<br>
&nbsp; Cụ thể quản lý gồm có thêm, xóa, sửa các sản phẩm (bánh), danh mục,… giúp quản lý cửa hàng tiện hơn.<br>
&nbsp; Còn khách hàng có 2 loại khách hàng. Khách hàng có tài khoản sẽ khác với khách hàng không có tài khoản ở thanh toán là khách vãng lai. Khách vãng lai có thể thêm sản phẩm vào giỏ và thanh toán . Cả 2 loại khách hàng đều được xem thông tin sản phẩm (bánh), danh mục…Sau khi họ đặt bánh nhân viên sẽ gọi điện xác nhận đơn hàng.

<H3>3. Công nghệ</H3>
Frontend: 
<ul>
  <li>HTML</li>
  <li>CSS</li>
  <li>JS</li>
  <li>Thymeleaf</li>
</ul>
Backend: 
<ul>
  <li>Springboot 3.0.12</li>
  <li>WebService MVC, RestfulAPI</li>
  <li> Java 17</li>
  <li> Maven </li>
  <li>JPA</li>
  <li>Lombok</li>
</ul>
Database: MySql

<h3>4. Flowchart's springboot</h3>
<img src="https://github.com/user-attachments/assets/50de7108-ee8f-40ce-9fa3-3523a3e6d45b" alt="Cant load img.." width="400" height="200" />
<br>

<H3>5. Cài đặt</H3>
<ul>
    <li>Cài đặt java phiên bản 17: https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html</li>
    <li>Cài đặt intellij: https://youtu.be/wPZas8c00GA?si=E50HI1gdpNDv7frj</li>
    <li>Giải nén -> import project vào intellij</li>
    <li>Cài đặt xampp server</li>
    <li>Load maven project -> load folder:</li>
    <li>Khởi động chạy modul admin hoặc khách hàng.</li>
</ul>
<br>

<H3>6. Địa chỉ máy chủ test</H3>
Do chưa tích hợp docker nên chỉ chạy được module hiện tại cho một loại user
<ul>
  <li>
    Trang chủ cửa hàng
    <ul><li>../localhost:8082/shop/</li></ul>
  </li>
</ul>
<ul>
  <li>
    Trang chủ admin
    <ul><li>../localhost:8082/admin/</li></ul>
  </li>
</ul>

<h3>7. Demo giao diện</h3>
<ul>
  <li><b>Một số giao diện cửa hàng</b></li>
</ul>
  <img style="margin-bottom: 100px" src="https://github.com/user-attachments/assets/4d0c9c1e-3131-452f-b2c6-9bf905c4f1ed" width="50%"  alt="Cant load img..">
  <img style="margin-bottom: 100px" src="https://github.com/user-attachments/assets/2a8c209c-2ae7-41af-b3c5-1373534730b8" width="50%"  alt="Cant load img..">
  <img src="https://github.com/user-attachments/assets/2a8c209c-2ae7-41af-b3c5-1373534730b8" width="50%" alt="Cant load img..">
  <img src="https://github.com/user-attachments/assets/96fab2f7-ecd3-4c9a-a68f-9262f2ec28d8" width="50%" alt="Cant load img..">
  <img src="https://github.com/user-attachments/assets/7b2826be-89fe-48db-8656-d4ed4117b0fa" width="50%" alt="Cant load img..">
  <img src="https://github.com/user-attachments/assets/623cf492-e5e4-4801-871a-63af8dc720c4" width="50%" alt="Cant load img..">
  <img src="https://github.com/user-attachments/assets/115b57d0-c2b7-4c3e-a7db-5a019da307be" width="50%" alt="Cant load img..">
  <img src="https://github.com/user-attachments/assets/2f3daa83-64cc-4570-a972-9fd6f91207bfe" width="50%" alt="Cant load img..">
  <img src="https://github.com/user-attachments/assets/0d2d6b65-55fc-4151-b89a-38cbdc008531" width="50%" alt="Cant load img..">
  <img src="https://github.com/user-attachments/assets/9fc16139-cb0f-40b8-9dd9-a8f6978c4451" width="50%" alt="Cant load img..">
  <img src="https://github.com/user-attachments/assets/5ccaa292-4c37-41c7-84c5-f53550587721" width="50%" alt="Cant load img..">
  <img src="https://github.com/user-attachments/assets/823724b9-c358-46c0-b031-0a441560d861" width="50%" alt="Cant load img..">
  <img src="https://github.com/user-attachments/assets/a77bdb95-e161-4159-b25d-0ee3cfe23b7d" width="50%" alt="Cant load img..">
<ul>
  <li><b>Giao diện admin</b></li>
</ul>
  <img src="https://github.com/user-attachments/assets/50c6aa66-e38e-4e15-a4d6-3f0d406d8739" width="50%" alt="Cant load img..">
  <img src="https://github.com/user-attachments/assets/20eb8611-a5dc-4b2a-942e-4ea7f4b0a693" width="50%" alt="Cant load img..">
  <img src="https://github.com/user-attachments/assets/455288e3-1538-4804-9d56-612d0ba95e02" width="50%" alt="Cant load img..">
  <img src="https://github.com/user-attachments/assets/2939f9ce-293a-490a-b446-15033e896c39" width="50%" alt="Cant load img..">
  <img src="https://github.com/user-attachments/assets/d060d8a4-36aa-4ba3-8cdd-2581b1d22041" width="50%" alt="Cant load img..">
  <img src="https://github.com/user-attachments/assets/a530925f-6416-493a-8c40-378392f4e51d" width="50%" alt="Cant load img..">
  <img src="https://github.com/user-attachments/assets/fa1119e7-aa45-4b85-988d-bd7d649f4cd5" width="50%" alt="Cant load img..">
  <img src="https://github.com/user-attachments/assets/8cc400f6-fe0a-4b9b-96d8-bfaeb31f10e3" width="50%" alt="Cant load img..">
  <img src="https://github.com/user-attachments/assets/4a0bdb0d-e98a-4a3b-8c17-35c40f8902e5" width="50%" alt="Cant load img..">
  

