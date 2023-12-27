import { HttpStatus, Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Theme } from 'src/theme/entities/theme.entity';
import { Repository } from 'typeorm';
import { ScreenAccess } from './entities/screen-access.entity';
import { Screen } from './entities/screen.entity';

@Injectable()
export class ScreenService {
  constructor(
    @InjectRepository(Screen)
    private screenRepository: Repository<Screen>,

    @InjectRepository(ScreenAccess)
    private screenAccessRepository: Repository<ScreenAccess>,
    @InjectRepository(Theme)
    private themeRepository: Repository<Theme>,
  ) {}

  async findAll(): Promise<any> {
    const screenDate = await this.screenRepository.find({
      select: {
        moduleNo: true,
        moduleName: true,
        screenNo: true,
        screenName: true,
        screenType: true,
        sequence: true,
      },
    });

    return {
      statusCode: HttpStatus.OK,
      message: 'Screen list',
      screens: screenDate,
      length: screenDate.length,
    };
  }

  async findAllAccess(moduleNo: number, screenNo: number) {
    const screenAcess = await this.screenAccessRepository.find({
      where: { moduleNo: moduleNo, screenNo: screenNo },
    });

    return {
      statusCode: HttpStatus.OK,
      message: 'Screen Access list',
      screenAccess: screenAcess,
      length: screenAcess.length,
    };
  }

  async companySelection(cmpCode: string, groupCode: string) {
    const screenAccess = await this.screenAccessRepository
      .createQueryBuilder('screenAccess')
      .leftJoinAndSelect('screenAccess.screen', 'screen')
      .select([
        'screenAccess.module_no as moduleNo',
        'screenAccess.screen_no as screenNo',
        'screenAccess.group_code as groupCode',
        'screenAccess.group_name as groupName',
        'screenAccess.cmp_code as cmpCode',
        'screenAccess.create_access as createAccess',
        'screenAccess.view_access as viewAccess',
        'screenAccess.edit_access as editAccess',
        'screenAccess.delete_access as deleteAccess',
        'screen.module_name as moduleName',
        'screen.screen_name as screenName',
      ])
      .where('screenAccess.cmp_code = :cmpCode', { cmpCode: cmpCode })
      .andWhere('screenAccess.group_code = :groupCode', {
        groupCode: groupCode,
      })
      .getRawMany();

    const themeList = await this.themeRepository.find({
      where: { cmpCode: cmpCode, groupCode: groupCode },
    });

    return {
      statusCode: HttpStatus.OK,
      message: 'Company Selection',
      theme: themeList,
      screenList: screenAccess,
      length: screenAccess.length,
    };
  }
}
