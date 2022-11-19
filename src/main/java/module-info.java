module app.finalprojectfx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens app.finalprojectfx to javafx.fxml;
    exports app.finalprojectfx;
    exports app.finalprojectfx.login;
    exports app.finalprojectfx.register;
    exports app.finalprojectfx.dashboard;
    exports app.finalprojectfx.usersManagement;
    exports app.finalprojectfx.supplier;
    exports app.finalprojectfx.addSupplier;
    exports app.finalprojectfx.db.model;
    exports app.finalprojectfx.alert;
    exports app.finalprojectfx.validator;
    exports app.finalprojectfx.addInventoryProduct;
    exports app.finalprojectfx.addInvoiceProduct;
    exports app.finalprojectfx.inventory;
    exports app.finalprojectfx.invoice;
    exports app.finalprojectfx.saleInvoices;
    exports app.finalprojectfx.overview;
    exports app.finalprojectfx.giftCards;
    opens app.finalprojectfx.login to javafx.fxml;
    opens app.finalprojectfx.register to javafx.fxml;
    opens app.finalprojectfx.dashboard to javafx.fxml;
    opens app.finalprojectfx.usersManagement to javafx.fxml;
    opens app.finalprojectfx.supplier to javafx.fxml;
    opens app.finalprojectfx.addSupplier to javafx.fxml;
    opens app.finalprojectfx.db.model to javafx.fxml;
    opens app.finalprojectfx.alert to javafx.fxml;
    opens app.finalprojectfx.validator to javafx.fxml;
    opens app.finalprojectfx.addInventoryProduct to javafx.fxml;
    opens app.finalprojectfx.addInvoiceProduct to javafx.fxml;
    opens app.finalprojectfx.inventory to javafx.fxml;
    opens app.finalprojectfx.invoice to javafx.fxml;
    opens app.finalprojectfx.saleInvoices to javafx.fxml;
    opens app.finalprojectfx.overview to javafx.fxml;
    opens app.finalprojectfx.giftCards to javafx.fxml;
    exports app.finalprojectfx.domain;
    opens app.finalprojectfx.domain to javafx.fxml;
    exports app.finalprojectfx.db;
    opens app.finalprojectfx.db to javafx.fxml;
}