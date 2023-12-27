import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {OrderbookingComponent} from '../orderbooking/orderbooking.component';
import {StringConstants} from '../service/stringconstants';

@Component({
  selector: 'app-deleteconfirmation',
  templateUrl: './deleteconfirmation.component.html',
  styleUrls: ['./deleteconfirmation.component.css']
})
export class DeleteconfirmationComponent implements OnInit {

  constructor(public dialogRef: MatDialogRef<OrderbookingComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any) {
  }

  ngOnInit(): void {
  }

  onClose(): void {
    this.dialogRef.close(StringConstants.CONST_NO);
  }

  onDelete(): void {
    this.dialogRef.close(StringConstants.CONST_YES);
  }
}
