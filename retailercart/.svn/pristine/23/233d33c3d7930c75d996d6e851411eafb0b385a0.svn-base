import {Component, Inject, OnInit} from '@angular/core';
import {DataService} from '../service/dataservice';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';

@Component({
  selector: 'app-languagedialog',
  templateUrl: './languagedialog.component.html',
  styleUrls: ['./languagedialog.component.css']
})
export class LanguagedialogComponent implements OnInit {

  languages = [];

  constructor(private dataService: DataService, public dialogRef: MatDialogRef<LanguagedialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any) {
    this.dataService.getLanguages().subscribe(result => {
      this.languages = result as any[];
    }, error => console.error(error));
  }

  ngOnInit(): void {
  }

  languageChange(val: any) {
    this.dialogRef.close(val);
  }

  setSelectedValue(code: string) {
    return code === this.data.lang;
  }
}
