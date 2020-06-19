import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAttr, Attr } from 'app/shared/model/attr.model';
import { AttrService } from './attr.service';
import { IAttrValue } from 'app/shared/model/attr-value.model';
import { AttrValueService } from 'app/entities/attr-value/attr-value.service';

@Component({
  selector: 'jhi-attr-update',
  templateUrl: './attr-update.component.html'
})
export class AttrUpdateComponent implements OnInit {
  isSaving = false;
  attrvalues: IAttrValue[] = [];

  editForm = this.fb.group({
    id: [],
    attrName: [],
    attrDesc: [],
    attrValueId: []
  });

  constructor(
    protected attrService: AttrService,
    protected attrValueService: AttrValueService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ attr }) => {
      this.updateForm(attr);

      this.attrValueService.query().subscribe((res: HttpResponse<IAttrValue[]>) => (this.attrvalues = res.body || []));
    });
  }

  updateForm(attr: IAttr): void {
    this.editForm.patchValue({
      id: attr.id,
      attrName: attr.attrName,
      attrDesc: attr.attrDesc,
      attrValueId: attr.attrValueId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const attr = this.createFromForm();
    if (attr.id !== undefined) {
      this.subscribeToSaveResponse(this.attrService.update(attr));
    } else {
      this.subscribeToSaveResponse(this.attrService.create(attr));
    }
  }

  private createFromForm(): IAttr {
    return {
      ...new Attr(),
      id: this.editForm.get(['id'])!.value,
      attrName: this.editForm.get(['attrName'])!.value,
      attrDesc: this.editForm.get(['attrDesc'])!.value,
      attrValueId: this.editForm.get(['attrValueId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAttr>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IAttrValue): any {
    return item.id;
  }
}
