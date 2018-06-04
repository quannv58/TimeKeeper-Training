package com.gem.timekeeper.realm;

import com.gem.timekeeper.data.dto.CompleteSurveyListDTO;
import com.gem.timekeeper.data.dto.SurveyDTO;
import com.gem.timekeeper.realm.model.CompleteSurveyRealmObject;
import com.gem.timekeeper.realm.model.SurveyRealmObject;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by admin on 11/19/2017.
 * RealmController
 */

public class RealmController {
  private static volatile RealmController mInstance;
  private final Realm mRealm;

  private RealmController() {
    mRealm = Realm.getDefaultInstance();
  }

  public synchronized static RealmController getInstance() {
    if (mInstance == null) {
      mInstance = new RealmController();
    }
    return mInstance;
  }

  public Realm getRealm() {
    return mRealm;
  }

  // Refresh the realm instance
  public void refresh() {
    mRealm.refresh();
  }

  public void clearAllSurvey(){
    mRealm.beginTransaction();
    mRealm.delete(SurveyRealmObject.class);
    mRealm.commitTransaction();
  }

  public List<SurveyDTO> getAllSurvey(){
    List<SurveyDTO> result = new ArrayList<>();
    RealmResults<SurveyRealmObject> realmResults = mRealm.where(SurveyRealmObject.class).findAll();
    for (SurveyRealmObject surveyRealmObject : realmResults) {
      result.add(surveyRealmObject.toDTO());
    }
    return result;
  }

  public long getSurveyListSize() {
    return mRealm.where(SurveyRealmObject.class).count();
  }

  public SurveyDTO getSurveyById(int surveyId) {
    RealmResults<SurveyRealmObject> listSurvey = mRealm.where(SurveyRealmObject.class)
        .equalTo("id", surveyId)
        .findAll();
    if (listSurvey != null && !listSurvey.isEmpty()) {
      return listSurvey.first().toDTO();
    } else {
      return null;
    }
  }

  public void saveSurveyList(final List<SurveyDTO> surveyDTOs) {
    clearAllSurvey();
    mRealm.executeTransaction(new Realm.Transaction() {
      @Override
      public void execute(Realm realm) {
        for (SurveyDTO surveyDTO : surveyDTOs) {
          mRealm.copyToRealmOrUpdate(new SurveyRealmObject(surveyDTO));
        }
      }
    });
  }

  public void saveCompleteSurvey(final int locationId, final String submitted, final String title, final String surveyData) {
    mRealm.executeTransaction(new Realm.Transaction() {
      @Override
      public void execute(Realm realm) {
        CompleteSurveyRealmObject object = realm.createObject(CompleteSurveyRealmObject.class);
        // CompleteSurveyRealmObject object = realm.createObject(CompleteSurveyRealmObject.class, UUID.randomUUID().toString());
        object.setLocationId(locationId);
        object.setSubmitted(submitted);
        object.setTitle(title);
        object.setSurveyData(surveyData);
      }
    });
  }

  public List<CompleteSurveyListDTO.CompleteSurveyDTO> getAllCompleteSurvey(){
    List<CompleteSurveyListDTO.CompleteSurveyDTO> result = new ArrayList<>();
    RealmResults<CompleteSurveyRealmObject> realmResults = mRealm.where(CompleteSurveyRealmObject.class).findAll();
    for (CompleteSurveyRealmObject realmObject : realmResults) {
      result.add(realmObject.toDTO());
    }
    return result;
  }

  public void deleteCompleteSurvey(){
    mRealm.executeTransaction(new Realm.Transaction() {
      @Override
      public void execute(Realm realm) {
        RealmResults<CompleteSurveyRealmObject> rows = realm.where(CompleteSurveyRealmObject.class).findAll();
        rows.deleteAllFromRealm();
      }
    });
  }

//  public void saveProductAsRecent(final ProductDTO productDTO) {
//    mRealm.executeTransaction(new Realm.Transaction() {
//      @Override
//      public void execute(Realm realm) {
//        mRealm.copyToRealmOrUpdate(productDTO);
//        RealmResults<ProductDTO> listProduct = mRealm.where(ProductDTO.class)
//            .equalTo("isFavorite", false)
//            .findAll()
//            .sort("mUpdateTime", Sort.DESCENDING);
//
//        while (listProduct.size() > SAVE_RECENT_AMOUNT) {
//          listProduct.get(SAVE_RECENT_AMOUNT).deleteFromRealm();
//        }
//      }
//    });
//  }
//
//  public void saveProductAsFavorite(final ProductDTO productDTO) {
//    mRealm.executeTransaction(new Realm.Transaction() {
//      @Override
//      public void execute(Realm realm) {
//        mRealm.copyToRealmOrUpdate(productDTO);
//      }
//    });
//  }
//
//  public void deleteRecentProduct(final String productId){
//    mRealm.executeTransaction(new Realm.Transaction() {
//      @Override
//      public void execute(Realm realm) {
//        RealmResults<ProductDTO> list = mRealm.where(ProductDTO.class)
//            .equalTo("isFavorite", false)
//            .equalTo("productId", productId)
//            .findAll();
//        list.deleteAllFromRealm();
//      }
//    });
//  }
//
//  public void deleteFavoriteProduct(final String productId){
//    mRealm.executeTransaction(new Realm.Transaction() {
//      @Override
//      public void execute(Realm realm) {
//        RealmResults<ProductDTO> list = mRealm.where(ProductDTO.class)
//            .equalTo("isFavorite", true)
//            .equalTo("productId", productId)
//            .findAll();
//        list.deleteAllFromRealm();
//      }
//    });
//  }
}
