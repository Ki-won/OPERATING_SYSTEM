package com.koreatech.ifteam.operating_system.UI;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.util.List;

public class ProcessResult extends VBox {
    private TableView<Result> resultTable;

    public ProcessResult() {
        // 테이블 생성
        resultTable = new TableView<>();

        // 테이블 컬럼 생성
        TableColumn<Result, Integer> btCol = new TableColumn<>("BT");
        btCol.setCellValueFactory(new PropertyValueFactory<>("bt"));

        TableColumn<Result, Integer> atCol = new TableColumn<>("AT");
        atCol.setCellValueFactory(new PropertyValueFactory<>("at"));

        TableColumn<Result, Integer> aatCol = new TableColumn<>("AAT");
        aatCol.setCellValueFactory(new PropertyValueFactory<>("aat"));

        TableColumn<Result, Integer> ttaCol = new TableColumn<>("TTA");
        ttaCol.setCellValueFactory(new PropertyValueFactory<>("tta"));

        TableColumn<Result, Integer> rtaCol = new TableColumn<>("RTA");
        rtaCol.setCellValueFactory(new PropertyValueFactory<>("rta"));

        // 테이블에 컬럼 추가
        resultTable.getColumns().addAll(btCol, atCol, aatCol, ttaCol, rtaCol);

        // VBox에 테이블 추가
        this.getChildren().add(resultTable);
    }

    // 결과를 테이블에 추가하는 메소드
    public void addResult(int bt, int at, int aat, int tta, int rta) {
        resultTable.getItems().add(new Result(bt, at, aat, tta, rta));
    }

    // 결과를 초기화하는 메소드
    public void clearResult() {
        resultTable.getItems().clear();
    }

    // 결과를 반환하는 메소드
    public List<Result> getResultList() {
        return resultTable.getItems();
    }

    // 결과 객체
    public static class Result {
        private final Integer bt;
        private final Integer at;
        private final Integer aat;
        private final Integer tta;
        private final Integer rta;

        public Result(int bt, int at, int aat, int tta, int rta) {
            this.bt = bt;
            this.at = at;
            this.aat = aat;
            this.tta = tta;
            this.rta = rta;
        }

        public Integer getBt() {
            return bt;
        }

        public Integer getAt() {
            return at;
        }

        public Integer getAat() {
            return aat;
        }

        public Integer getTta() {
            return tta;
        }

        public Integer getRta() {
            return rta;
        }
    }
}

