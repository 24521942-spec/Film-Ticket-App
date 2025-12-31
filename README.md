# Movie Ticket Booking App

Movie Ticket Booking App là ứng dụng đặt vé xem phim trực tuyến, cho phép người dùng tìm kiếm phim, chọn rạp, suất chiếu, ghế ngồi, mua bắp nước và nhận vé dưới dạng QR Code. Hệ thống được xây dựng theo mô hình Client – Server, bao gồm ứng dụng Android cho người dùng và hệ thống quản trị dành cho Admin.

Ứng dụng hỗ trợ đầy đủ các chức năng cơ bản của một hệ thống đặt vé xem phim hiện đại như quản lý người dùng, phim, suất chiếu, vé và lịch sử đặt vé. Giao diện được thiết kế theo Material Design, thân thiện với người dùng và phù hợp với các thiết bị Android.

Hệ thống bao gồm hai phần chính: ứng dụng Android dành cho người dùng và Backend được xây dựng bằng Spring Boot cung cấp RESTful API, kết nối với cơ sở dữ liệu MySQL để quản lý và lưu trữ dữ liệu tập trung. Admin có thể quản lý phim, phòng chiếu, ghế ngồi, suất chiếu và vé thông qua hệ thống quản trị riêng.

Các chức năng chính của người dùng bao gồm đăng ký và đăng nhập tài khoản, xem danh sách phim đang chiếu, xem chi tiết phim, lựa chọn rạp và suất chiếu, chọn ghế ngồi, mua vé và bắp nước, thanh toán và nhận vé dưới dạng QR Code, xem lịch sử vé đã đặt và quản lý thông tin cá nhân. Đối với Admin, hệ thống hỗ trợ quản lý phim, phòng chiếu, ghế ngồi, suất chiếu, vé, đơn hàng và theo dõi lịch sử đặt vé của người dùng.

Ứng dụng được phát triển bằng Android Studio với ngôn ngữ Java và XML cho giao diện. Backend sử dụng Spring Boot, áp dụng mô hình RESTful API kết hợp JPA/Hibernate để thao tác với cơ sở dữ liệu MySQL. Việc giao tiếp giữa Android App và Backend được thực hiện thông qua Retrofit. Hệ thống có áp dụng xác thực người dùng, quản lý session và phân quyền giữa User và Admin nhằm đảm bảo tính bảo mật.

Dự án áp dụng các kiến thức quan trọng như lập trình Android, thiết kế RESTful API, mô hình Client – Server, thiết kế cơ sở dữ liệu và kiến trúc phân lớp (Layered Architecture). Đây là đồ án phục vụ mục đích học tập và nghiên cứu trong môn học phát triển ứng dụng di động.
