# 远程代码沙箱

本项目提供了一个安全高效的远程代码执行平台，支持使用 Java 原生方式或 Docker 容器运行用户提交的 Java 代码。项目设计旨在保障运行环境的安全性与可靠性，同时提供良好的可扩展性。

---

## 功能特性

1. **代码提交与执行**
    - 接收用户提交的 Java 代码。
    - 安全地编译和运行代码，限制资源使用。

2. **安全机制**
    - 预防恶意代码运行：
        - 长时间运行的进程自动超时终止。
        - JVM 最大内存限制（`-Xmx256m`）。
        - 危险操作关键字黑名单。
        - 自定义 `SecurityManager` 实现权限控制。
    - Docker 容器隔离，提升安全性。

3. **资源控制**
    - 限制内存和 CPU 使用量。
    - 可配置运行超时时间（默认：5000 毫秒）。

4. **错误处理**
    - 明确区分用户代码错误与沙箱内部错误。
    - 提供详细的错误报告与状态反馈。

5. **优化设计**
    - 采用 **模板方法设计模式**，减少代码重复，提高代码可维护性。