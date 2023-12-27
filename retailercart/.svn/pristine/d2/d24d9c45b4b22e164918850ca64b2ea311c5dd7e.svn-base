import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {OrderbookingComponent} from '../orderbooking/orderbooking.component';
import {StringConstants} from '../service/stringconstants';

@Component({
  selector: 'app-orderconfirmdialog',
  templateUrl: './orderconfirmdialog.component.html',
  styleUrls: ['./orderconfirmdialog.component.css']
})
export class OrderconfirmdialogComponent implements OnInit {

  constructor(public dialogRef: MatDialogRef<OrderbookingComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any) {
  }

  ngOnInit(): void {
  }

  onClose(): void {
    this.dialogRef.close(StringConstants.CONST_NO);
  }

  onConfirm(): void {
    this.dialogRef.close(StringConstants.CONST_YES);
  }
}
