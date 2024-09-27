package com.example.marinepath.entity.Enum;

public enum PortStatusEnum {
    OPERATIONAL,     // Cảng đang hoạt động bình thường
    MAINTENANCE,     // Cảng đang trong quá trình bảo trì
    CLOSED,          // Cảng đã đóng cửa
    CONGESTED,       // Cảng đang quá tải (tắc nghẽn)
    UNDER_CONSTRUCTION, // Cảng đang được xây dựng hoặc nâng cấp
    WEATHER_DELAY,   // Cảng bị ảnh hưởng bởi thời tiết xấu
    SECURITY_ALERT,  // Cảng có cảnh báo an ninh
    LIMITED_OPERATION, // Cảng hoạt động hạn chế (do sự cố, thời tiết hoặc sự kiện khác)
    EMERGENCY_CLOSED, // Cảng đóng cửa khẩn cấp
    RESTRICTED,       // Cảng bị giới hạn tiếp cận
    DELETED
}
