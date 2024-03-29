package cn.zhonggong.makeup.service.impl;

import cn.zhonggong.makeup.domain.CarGoods;
import cn.zhonggong.makeup.enums.ResultEnum;
import cn.zhonggong.makeup.repository.CarGoodsRepository;
import cn.zhonggong.makeup.service.CarGoodsService;
import cn.zhonggong.makeup.util.ResultVOUtil;
import cn.zhonggong.makeup.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * 军辉
 * 2019-07-19 19:22
 */
@Slf4j
@Service
public class CarGoodsServiceImpl implements CarGoodsService {

    @Autowired
    private CarGoodsRepository carGoodsRepository;

    @Override
    public ResultVO addCarGoods(CarGoods carGoods) {
        if (carGoods == null || "".equals(carGoods)) {
            return ResultVOUtil.Fail("所添加商品为空,请检查后再添加");
        } else {
            log.info("所添加的商品：" + carGoods);
            carGoods.setGoodsCount(1);
            CarGoods dbCargoods = carGoodsRepository.findByGoodsId(carGoods.getGoodsId());

            /*首先获取当前用户的购物车商品
             *查看当前商品是否在购物车内
             * 如果在。其数量 ＋ 当前商品的数量（1），
             * 如果不在，增加其对象至数据库
             * */
            //说明此物品已存在购物车内
            List<CarGoods> carGoodsList = carGoodsRepository.findByUserId(carGoods.getUserId());
            log.info("userId： " + carGoods.getUserId() + " 的购物车商品：" + carGoodsList);
            if (carGoodsList.contains(dbCargoods)) {
                log.info("此物品已存在购物车其数据：" + dbCargoods);
                dbCargoods.setGoodsCount(dbCargoods.getGoodsCount() + carGoods.getGoodsCount());
                return ResultVOUtil.Success("已添加商品" + carGoods + "至购物车", 1, carGoods);
            } else {
                carGoodsRepository.save(carGoods);
                return ResultVOUtil.Success("已添加商品" + carGoods + "至购物车", 1, carGoods);
            }

        }
    }

    @Override
    public ResultVO selectCarGoodsByGoodsId(int goodsId) {
        if (goodsId <= 0) {
            return ResultVOUtil.Fail("检查商品goodsId是否有误");
        } else {
            CarGoods carGoods = carGoodsRepository.findByGoodsId(goodsId);
            if (carGoods == null) {
                return ResultVOUtil.Fail("所查商品不在购物车内");
            } else {
                return ResultVOUtil.Success("检索成功", 1, carGoods);
            }

        }
    }

    @Override
    public ResultVO deleteCarGoodsByGoodsId(int goodsId) {
        if (goodsId <= 0) {
            return ResultVOUtil.Fail("检查商品id是否有误");
        } else {
            CarGoods carGoods = carGoodsRepository.findByGoodsId(goodsId);
            if (carGoods == null) {
                return ResultVOUtil.Fail("商品不存在您的购物车中");
            } else if (carGoods.getGoodsCount() == 1) {
                carGoodsRepository.deleteByGoodsId(goodsId);
                return ResultVOUtil.Success("从购物车中删除 " + goodsId + " 号的商品成功");
            } else {
                carGoods.setGoodsCount(carGoods.getGoodsCount() - 1);
                return ResultVOUtil.Success("从购物车中 " + goodsId + " 号的商品,数量 - 1");
            }

        }
    }


    @Override
    public ResultVO selectCarGoodsByUserId(int userId) {
        System.out.println(userId);
        List<CarGoods> carGoodsList = null;
        if (userId <= 0) {
            ResultVOUtil.Fail("用户id有误");
        } else {
            carGoodsList = carGoodsRepository.findByUserId(userId);
            if (carGoodsList == null || carGoodsList.size() == 0) {
                return ResultVOUtil.Fail("当前用户的购物车为空");
            }
        }
        log.info("id " + userId + "的用户的购物车" + carGoodsList);
        return ResultVOUtil.Success("查询id " + userId + "的用户的购物车成功", carGoodsList.size(), carGoodsList);
    }

    @Override
    public ResultVO deleteCarGoodsById(int id) {
        if (id <= 0) {
            return ResultVOUtil.Fail("检查商品id是否有误");
        } else {
            int result = carGoodsRepository.deleteByGoodsId(id);
            return ResultVOUtil.Success("删除成功", 1, result);

        }
    }
}
