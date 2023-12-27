import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import { OrderconfirmdialogComponent } from '../orderconfirmdialog/orderconfirmdialog.component';
import { StringConstants } from '../service/stringconstants';

@Component({
  selector: 'app-confirmationdialog',
  templateUrl: './confirmationdialog.component.html',
  styleUrls: ['./confirmationdialog.component.css']
})
export class ConfirmationdialogComponent implements OnInit {

  isref:number=0;

  constructor(@Inject(MAT_DIALOG_DATA) public data: any, public dialogRef: MatDialogRef<OrderconfirmdialogComponent>) {
  }

  ngOnInit(): void {
    if(this.data.pageref)
    {
      this.isref = this.data.pageref;
    }
    
  }

  onClose(): void {
    this.dialogRef.close(StringConstants.CONST_NO);
  }

}
